package com.example.chessengineapp.engine;
;

import com.example.chessengineapp.core.*;
import com.example.chessengineapp.game.*;
import java.util.*;

public class Search {

    private Evaluator evaluator = new Evaluator();
    private MoveValidator validator = new MoveValidator();

    public int minimax(Board board, int depth, int alpha, int beta, boolean isMaximizing) {

        if (depth == 0) {
            return evaluator.evaluate(board);
        }

        Color currentColor = isMaximizing ? Color.WHITE : Color.BLACK;

        List<Move> moves = getAllMoves(board, currentColor);

        if (moves.isEmpty()) {
            return evaluator.evaluate(board);
        }

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;

            for (Move move : moves) {
                Board copy = new Board(board);
                copy.movePiece(move);

                int eval = minimax(copy, depth - 1, alpha, beta, false);

                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);

                if (beta <= alpha) {
                    break; // pruning
                }
            }

            return maxEval;

        } else {
            int minEval = Integer.MAX_VALUE;

            for (Move move : moves) {
                Board copy = new Board(board);
                copy.movePiece(move);

                int eval = minimax(copy, depth - 1, alpha, beta, true);

                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);

                if (beta <= alpha) {
                    break; // pruning
                }
            }

            return minEval;
        }
    }

    private List<Move> getAllMoves(Board board, Color color) {
        List<Move> moves = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                Position pos = new Position(i, j);
                Piece piece = board.getPiece(pos);

                if (piece != null && piece.getColor() == color) {
                    moves.addAll(validator.getLegalMoves(board, pos));
                }
            }
        }

        return moves;
    }

}