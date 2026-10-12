package com.example.twoplayergame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    private lateinit var winnerTextView: TextView
    private lateinit var resetGameButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        winnerTextView = findViewById(R.id.winnerTextView)
        resetGameButton = findViewById(R.id.resetGameButton)

        val winnerName = intent.getStringExtra(TwoPlayerGame.EXTRA_WINNER_NAME)
            ?: getString(R.string.default_player1)

        winnerTextView.text = getString(R.string.winner_format, winnerName)

        resetGameButton.setOnClickListener {
            // 10. Go back to TwoPlayerGame (or start the TwoPlayerGame)
            // 10a. Reset the inputs to begin a new game.
            val intent = Intent(this, TwoPlayerGame::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                putExtra(TwoPlayerGame.EXTRA_RESET_GAME, true)
            }
            startActivity(intent)
            finish()
        }
    }
}
