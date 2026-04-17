package com.example.chessengineapp.engine;
;

import com.example.chessengineapp.core.*;
import java.util.*;

public class MoveGenerator {

    private static final int[][] rookDirections = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}
    };

    private static final int[][] bishopDirections = {
            {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
    };

    private static final int[][] queenDirections = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1},
            {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
    };

    public List<Move> generateMoves(Board board, Position pos) {
        Piece piece = board.getPiece(pos);

        if (piece == null) return new ArrayList<>();

        switch (piece.getType()) {
            case PAWN:
                return generatePawnMoves(board, pos, piece);
            case KNIGHT:
                return generateKnightMoves(board, pos, piece);
            case ROOK:
                return generateSlidingMoves(board, pos, piece, rookDirections);
            case BISHOP:
                return generateSlidingMoves(board, pos, piece, bishopDirections);
            case KING:
                return generateKingMoves(board, pos, piece);
            case QUEEN:
                return generateSlidingMoves(board, pos, piece, queenDirections);
            default:
                return new ArrayList<>();
        }
    }

    private List<Move> generatePawnMoves(Board board, Position pos, Piece piece) {
        List<Move> moves = new ArrayList<>();

        int direction = (piece.getColor() == Color.WHITE) ? -1 : 1;

        // Forward move
        Position forward = new Position(pos.getRow() + direction, pos.getCol());
        if (forward.isValid() && board.getPiece(forward) == null) {
            moves.add(new Move(pos, forward));
        }

        // Double move (only from starting position)
        int startRow = (piece.getColor() == Color.WHITE) ? 6 : 1;

        if (pos.getRow() == startRow) {
            Position doubleForward = new Position(pos.getRow() + 2 * direction, pos.getCol());

            if (board.getPiece(forward) == null && board.getPiece(doubleForward) == null) {
                moves.add(new Move(pos, doubleForward));
            }
        }

        // Capture left
        Position left = new Position(pos.getRow() + direction, pos.getCol() - 1);
        if (left.isValid()) {
            Piece target = board.getPiece(left);
            if (target != null && target.getColor() != piece.getColor()) {
                moves.add(new Move(pos, left));
            }
        }

        // Capture right
        Position right = new Position(pos.getRow() + direction, pos.getCol() + 1);
        if (right.isValid()) {
            Piece target = board.getPiece(right);
            if (target != null && target.getColor() != piece.getColor()) {
                moves.add(new Move(pos, right));
            }
        }

        return moves;
    }

    private List<Move> generateKnightMoves(Board board, Position pos, Piece piece) {
        List<Move> moves = new ArrayList<>();

        int[][] offsets = {
                {-2, -1}, {-2, 1},
                {-1, -2}, {-1, 2},
                {1, -2}, {1, 2},
                {2, -1}, {2, 1}
        };

        for (int[] o : offsets) {
            Position newPos = new Position(pos.getRow() + o[0], pos.getCol() + o[1]);

            if (!newPos.isValid()) continue;

            Piece target = board.getPiece(newPos);

            if (target == null || target.getColor() != piece.getColor()) {
                moves.add(new Move(pos, newPos));
            }
        }

        return moves;
    }

    private List<Move> generateSlidingMoves(Board board, Position pos, Piece piece, int[][] directions) {
        List<Move> moves = new ArrayList<>();

        for (int[] dir : directions) {
            int row = pos.getRow();
            int col = pos.getCol();

            while (true) {
                row += dir[0];
                col += dir[1];

                Position newPos = new Position(row, col);

                if (!newPos.isValid()) break;

                Piece target = board.getPiece(newPos);

                if (target == null) {
                    moves.add(new Move(pos, newPos));
                } else {
                    if (target.getColor() != piece.getColor()) {
                        moves.add(new Move(pos, newPos));
                    }
                    break;
                }
            }
        }

        return moves;
    }
    private List<Move> generateKingMoves(Board board, Position pos, Piece piece) {
        List<Move> moves = new ArrayList<>();

        int[][] directions = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1},          {0, 1},
                {1, -1}, {1, 0}, {1, 1}
        };

        for (int[] d : directions) {
            Position newPos = new Position(pos.getRow() + d[0], pos.getCol() + d[1]);

            if (!newPos.isValid()) continue;

            Piece target = board.getPiece(newPos);

            if (target == null || target.getColor() != piece.getColor()) {
                moves.add(new Move(pos, newPos));
            }
        }

        return moves;
    }
}