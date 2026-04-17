package com.example.chessengineapp.core;

public class Board {
    private Piece[][] board;
    public Board() {
        board = new Piece[8][8];
        initialize();
    }
    public Board(Board other) {
        board = new Piece[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = other.board[i][j];

                if (p != null) {
                    board[i][j] = new Piece(p.getType(), p.getColor());
                } else {
                    board[i][j] = null;
                }
            }
        }
    }

    // ✅ Get piece
    public Piece getPiece(Position pos) {
        if (!pos.isValid()) return null;
        return board[pos.getRow()][pos.getCol()];
    }

    // ✅ Set piece
    public void setPiece(Position pos, Piece piece) {
        if (pos.isValid()) {
            board[pos.getRow()][pos.getCol()] = piece;
        }
    }

    // ✅ Move piece
    public void movePiece(Move move) {
        Piece piece = getPiece(move.getFrom());
        setPiece(move.getTo(), piece);
        setPiece(move.getFrom(), null);
    }

    // ♟️ Initialize starting position
    private void initialize() {

        // Pawns
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Piece(PieceType.PAWN, Color.BLACK);
            board[6][i] = new Piece(PieceType.PAWN, Color.WHITE);
        }

        // Rooks
        board[0][0] = new Piece(PieceType.ROOK, Color.BLACK);
        board[0][7] = new Piece(PieceType.ROOK, Color.BLACK);
        board[7][0] = new Piece(PieceType.ROOK, Color.WHITE);
        board[7][7] = new Piece(PieceType.ROOK, Color.WHITE);

        // Knights
        board[0][1] = new Piece(PieceType.KNIGHT, Color.BLACK);
        board[0][6] = new Piece(PieceType.KNIGHT, Color.BLACK);
        board[7][1] = new Piece(PieceType.KNIGHT, Color.WHITE);
        board[7][6] = new Piece(PieceType.KNIGHT, Color.WHITE);

        // Bishops
        board[0][2] = new Piece(PieceType.BISHOP, Color.BLACK);
        board[0][5] = new Piece(PieceType.BISHOP, Color.BLACK);
        board[7][2] = new Piece(PieceType.BISHOP, Color.WHITE);
        board[7][5] = new Piece(PieceType.BISHOP, Color.WHITE);

        // Queens
        board[0][3] = new Piece(PieceType.QUEEN, Color.BLACK);
        board[7][3] = new Piece(PieceType.QUEEN, Color.WHITE);

        // Kings
        board[0][4] = new Piece(PieceType.KING, Color.BLACK);
        board[7][4] = new Piece(PieceType.KING, Color.WHITE);
    }

    // 🖥️ Print board (for console testing)
    public void printBoard() {
        for (int i = 0; i < 8; i++) {
            System.out.print((8 - i) + " ");

            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    System.out.print(". ");
                } else {
                    System.out.print(board[i][j] + " ");
                }
            }
            System.out.println();
        }

        System.out.println("  a b c d e f g h");
    }
}