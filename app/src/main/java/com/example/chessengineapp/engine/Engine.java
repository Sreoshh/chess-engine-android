package com.example.chessengineapp.engine;

import com.example.chessengineapp.core.*;
import com.example.chessengineapp.game.*;
import java.util.*;

public class Engine {

    private Search search = new Search();
    private MoveValidator validator = new MoveValidator();

    public Move findBestMove(Board board, Color color) {
        List<Move> moves = getAllMoves(board, color);
        Collections.shuffle(moves);
        Move bestMove = null;
        int bestValue = (color == Color.WHITE) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (Move move : moves) {
            Board copy = new Board(board);
            copy.movePiece(move);

            int eval = search.minimax(copy, 3, Integer.MIN_VALUE, Integer.MAX_VALUE, color != Color.WHITE);

            if (color == Color.WHITE && eval > bestValue) {
                bestValue = eval;
                bestMove = move;
            }

            if (color == Color.BLACK && eval < bestValue) {
                bestValue = eval;
                bestMove = move;
            }
        }

        return bestMove;
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