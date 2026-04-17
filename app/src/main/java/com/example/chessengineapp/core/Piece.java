package com.example.chessengineapp.core;

public class Piece {
    private PieceType type;
    private Color color;

    public Piece(PieceType type, Color color) {
        this.type = type;
        this.color = color;
    }

    public PieceType getType() { return type; }
    public Color getColor() { return color; }

    @Override
    public String toString() {
        char symbol;

        switch (type) {
            case KING: symbol = 'K'; break;
            case QUEEN: symbol = 'Q'; break;
            case ROOK: symbol = 'R'; break;
            case BISHOP: symbol = 'B'; break;
            case KNIGHT: symbol = 'N'; break;
            case PAWN: symbol = 'P'; break;
            default: symbol = '?';
        }

        return color == Color.WHITE ? String.valueOf(symbol)
                : String.valueOf(Character.toLowerCase(symbol));
    }
}