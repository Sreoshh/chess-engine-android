package com.example.chessengineapp

import android.os.Bundle
import android.view.Gravity
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chessengineapp.core.*
import com.example.chessengineapp.engine.Engine
import com.example.chessengineapp.engine.Evaluator
import com.example.chessengineapp.game.Game

class MainActivity : AppCompatActivity() {

    private lateinit var grid: GridLayout
    private lateinit var game: Game
    private lateinit var engine: Engine
    private lateinit var evaluator: Evaluator

    private lateinit var scoreText: TextView
    private lateinit var turnText: TextView

    private var legalMoves: List<Move> = emptyList()
    private var selectedRow = -1
    private var selectedCol = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        grid = findViewById(R.id.chessBoard)
        scoreText = findViewById(R.id.scoreText)
        turnText = findViewById(R.id.turnText)

        game = Game()
        engine = Engine()
        evaluator = Evaluator()

        updateUI()
        createBoard()
    }

    private fun updateUI() {
        val score = evaluator.evaluate(game.board)
        scoreText.text = "Score: $score"
        turnText.text = "Turn: ${game.currentTurn}"
    }

    private fun createBoard() {

        for (row in 0 until 8) {
            for (col in 0 until 8) {

                val cell = TextView(this)
                val size = resources.displayMetrics.widthPixels / 8
                val piece = game.board.getPiece(Position(row, col))

                val params = GridLayout.LayoutParams().apply {
                    width = size
                    height = size
                    rowSpec = GridLayout.spec(row)
                    columnSpec = GridLayout.spec(col)
                }

                cell.layoutParams = params
                cell.textSize = 28f
                cell.gravity = Gravity.CENTER

                // 🎨 Board color
                if ((row + col) % 2 == 0) {
                    cell.setBackgroundColor(android.graphics.Color.parseColor("#E6CFA7"))
                } else {
                    cell.setBackgroundColor(android.graphics.Color.parseColor("#8B5A2B"))
                }

                // ♟️ Pieces
                if (piece != null) {
                    cell.text = when (piece.type) {
                        PieceType.PAWN -> if (piece.color == Color.WHITE) "♙" else "♟"
                        PieceType.ROOK -> if (piece.color == Color.WHITE) "♖" else "♜"
                        PieceType.KNIGHT -> if (piece.color == Color.WHITE) "♘" else "♞"
                        PieceType.BISHOP -> if (piece.color == Color.WHITE) "♗" else "♝"
                        PieceType.QUEEN -> if (piece.color == Color.WHITE) "♕" else "♛"
                        PieceType.KING -> if (piece.color == Color.WHITE) "♔" else "♚"
                    }

                    cell.setTextColor(
                        if (piece.color == Color.BLACK)
                            android.graphics.Color.BLACK
                        else
                            android.graphics.Color.WHITE
                    )
                }

                // 🎯 Highlight selected
                if (row == selectedRow && col == selectedCol) {
                    cell.setBackgroundColor(android.graphics.Color.YELLOW)
                }

                // 🎯 Highlight legal moves
                for (move in legalMoves) {
                    if (move.to.row == row && move.to.col == col) {
                        cell.setBackgroundColor(android.graphics.Color.parseColor("#BACA44"))
                    }
                }

                // 🎮 Click logic
                cell.setOnClickListener {

                    if (selectedRow == -1) {
                        val piece = game.board.getPiece(Position(row, col))
                        if (piece == null || piece.color != game.currentTurn) {
                            return@setOnClickListener
                        }

                        selectedRow = row
                        selectedCol = col
                        legalMoves = game.getLegalMoves(Position(row, col))

                        grid.removeAllViews()
                        createBoard()

                    } else {
                        val move = Move(
                            Position(selectedRow, selectedCol),
                            Position(row, col)
                        )

                        val success = game.makeMove(move)

                        if (!success) {
                            Toast.makeText(this, "Invalid move", Toast.LENGTH_SHORT).show()
                        } else {
                            // 🤖 AI move
                            val aiMove = engine.findBestMove(game.board, game.currentTurn)
                            if (aiMove != null) {
                                game.makeMove(aiMove)
                            }
                        }

                        if (game.isCheckmate()) {
                            Toast.makeText(this, "CHECKMATE!", Toast.LENGTH_LONG).show()
                        }

                        selectedRow = -1
                        selectedCol = -1
                        legalMoves = emptyList()

                        updateUI()
                        grid.removeAllViews()
                        createBoard()
                    }
                }

                grid.addView(cell)
            }
        }
    }
}