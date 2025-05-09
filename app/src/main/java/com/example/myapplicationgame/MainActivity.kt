package com.example.myapplicationgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var buttons: Array<Button>
    private lateinit var statusText: TextView
    private lateinit var resetButton: Button

    private var currentPlayer = "X"
    private var board = Array(9) { "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttons = arrayOf(
            findViewById(R.id.button1), findViewById(R.id.button2), findViewById(R.id.button3),
            findViewById(R.id.button4), findViewById(R.id.button5), findViewById(R.id.button6),
            findViewById(R.id.button7), findViewById(R.id.button8), findViewById(R.id.button9)
        )

        statusText = findViewById(R.id.statusText)
        resetButton = findViewById(R.id.resetButton)

        for (i in buttons.indices) {
            buttons[i].setOnClickListener { onButtonClick(i) }
        }

        resetButton.setOnClickListener { resetGame() }
    }

    private fun onButtonClick(index: Int) {
        if (board[index].isNotEmpty()) return

        board[index] = currentPlayer
        buttons[index].text = currentPlayer

        if (checkWin()) {
            statusText.text = "Player $currentPlayer Wins!"
            disableButtons()
        } else if (board.all { it.isNotEmpty() }) {
            statusText.text = "It's a Draw!"
        } else {
            currentPlayer = if (currentPlayer == "X") "O" else "X"
            statusText.text = "Player $currentPlayer's Turn"
        }
    }

    private fun checkWin(): Boolean {
        val winPositions = arrayOf(
            intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8), // Rows
            intArrayOf(0, 3, 6), intArrayOf(1, 4, 7), intArrayOf(2, 5, 8), // Columns
            intArrayOf(0, 4, 8), intArrayOf(2, 4, 6)                       // Diagonals
        )

        for (positions in winPositions) {
            if (board[positions[0]] == currentPlayer &&
                board[positions[1]] == currentPlayer &&
                board[positions[2]] == currentPlayer) {
                return true
            }
        }
        return false
    }

    private fun disableButtons() {
        for (button in buttons) {
            button.isEnabled = false
        }
    }

    private fun resetGame() {
        board = Array(9) { "" }
        currentPlayer = "X"
        statusText.text = "Player X's Turn"

        for (button in buttons) {
            button.text = ""
            button.isEnabled = true
        }
    }
}
