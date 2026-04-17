package com.example.chessengineapp.engine;
;

import com.example.chessengineapp.core.*;

public class Evaluator {

    public int evaluate(Board board) {
        int score = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                Position pos = new Position(i, j);
                Piece piece = board.getPiece(pos);

                if (piece != null) {
                    int value = getPieceValue(piece.getType());

                    // 🎯 Positional bonus
                    value += getPositionBonus(piece, i, j);

                    if (piece.getColor() == Color.WHITE) {
                        score += value;
                    } else {
                        score -= value;
                    }
                }
            }
        }

        return score;
    }

    private int getPositionBonus(Piece piece, int row, int col) {

        // 🎯 Center squares bonus
        if (row >= 2 && row <= 5 && col >= 2 && col <= 5) {
            return 1;
        }

        return 0;
    }

    private int getPieceValue(PieceType type) {
        switch (type) {
            case PAWN:
                return 1;
            case KNIGHT:
            case BISHOP:
                return 3;
            case ROOK:
                return 5;
            case QUEEN:
                return 9;
            case KING:
                return 100;
        }
        return 0;
    }
}