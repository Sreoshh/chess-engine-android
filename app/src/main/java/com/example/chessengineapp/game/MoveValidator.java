package com.example.chessengineapp.game;


import com.example.chessengineapp.core.*;
import com.example.chessengineapp.engine.*;
import java.util.*;

public class MoveValidator {

    private MoveGenerator generator = new MoveGenerator();

    public List<Move> getLegalMoves(Board board, Position pos) {
        List<Move> moves = generator.generateMoves(board, pos);
        List<Move> legalMoves = new ArrayList<>();

        Piece piece = board.getPiece(pos);
        if (piece == null) return legalMoves;

        for (Move move : moves) {
            Board copy = new Board (board);

            copy.movePiece(move);

            if (!isKingInCheck(copy, piece.getColor())) {
                legalMoves.add(move);
            }
        }

        return legalMoves;
    }

    public boolean isKingInCheck(Board board, Color color) {
        Position kingPos = findKing(board, color);

        // Check if any enemy move hits king
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Position pos = new Position(i, j);
                Piece p = board.getPiece(pos);

                if (p != null && p.getColor() != color) {
                    List<Move> moves = generator.generateMoves(board, pos);

                    for (Move m : moves) {
                        if (m.getTo().equals(kingPos)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    private Position findKing(Board board, Color color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Position pos = new Position(i, j);
                Piece p = board.getPiece(pos);

                if (p != null &&
                        p.getType() == PieceType.KING &&
                        p.getColor() == color) {
                    return pos;
                }
            }
        }
        return null;
    }
}