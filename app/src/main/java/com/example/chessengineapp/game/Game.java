package com.example.chessengineapp.game;


import com.example.chessengineapp.core.*;
import com.example.chessengineapp.engine.*;
import java.util.*;

public class Game {

    private Board board;
    private Color currentTurn;
    private MoveValidator validator;

    public Game() {
        board = new Board();
        currentTurn = Color.WHITE;
        validator = new MoveValidator();
    }

    public Board getBoard() {
        return board;
    }

    public Color getCurrentTurn() {
        return currentTurn;
    }

    public List<Move> getLegalMoves(Position pos) {
        Piece piece = board.getPiece(pos);

        if (piece == null || piece.getColor() != currentTurn) {
            return new ArrayList<>();
        }

        return validator.getLegalMoves(board, pos);
    }

    public boolean makeMove(Move move) {
        List<Move> legalMoves = getLegalMoves(move.getFrom());

        for (Move m : legalMoves) {
            if (m.getTo().equals(move.getTo())) {
                board.movePiece(move);
                currentTurn = currentTurn.opposite();
                return true;
            }
        }
        return false;
    }

    public boolean isCheckmate() {
        if (!validator.isKingInCheck(board, currentTurn)) {
            return false;
        }

        return !hasAnyLegalMoves(currentTurn);
    }

    public boolean isStalemate() {
        if (validator.isKingInCheck(board, currentTurn)) {
            return false;
        }

        return !hasAnyLegalMoves(currentTurn);
    }

    private boolean hasAnyLegalMoves(Color color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                Position pos = new Position(i, j);
                Piece piece = board.getPiece(pos);

                if (piece != null && piece.getColor() == color) {
                    if (!validator.getLegalMoves(board, pos).isEmpty()) {
                        return true;
                    }
                }
            }
        }


        return false;
    }
}



