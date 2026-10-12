package com.example.twoplayergame

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TwoPlayerGame : AppCompatActivity() {

    private lateinit var editText1: EditText
    private lateinit var editText2: EditText
    private lateinit var startGameButton: Button
    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var descriptionTextView: TextView
    private lateinit var scoreTextView: TextView

    private var player1Name: String = ""
    private var player2Name: String = ""
    private var player1Score: Int = 0
    private var player2Score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText1 = findViewById(R.id.editText1)
        editText2 = findViewById(R.id.editText2)
        startGameButton = findViewById(R.id.startGameButton)
        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        descriptionTextView = findViewById(R.id.descriptionTextView)
        scoreTextView = findViewById(R.id.scoreTextView)

        // Description text formatting with bold and italic
        descriptionTextView.text = Html.fromHtml(
            getString(R.string.image_description),
            Html.FROM_HTML_MODE_LEGACY
        )

        // Initial disabled state for player buttons
        button1.isEnabled = false
        button2.isEnabled = false

        startGameButton.setOnClickListener {
            onStartGameClicked()
        }

        button1.setOnClickListener {
            onPlayer1Clicked()
        }

        button2.setOnClickListener {
            onPlayer2Clicked()
        }

        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        if (intent?.getBooleanExtra(EXTRA_RESET_GAME, false) == true) {
            resetGame()
        }
    }

    private fun onStartGameClicked() {
        // 8. Update texts of button1 and button2 as player names.
        val input1 = editText1.text.toString().trim()
        val input2 = editText2.text.toString().trim()

        player1Name = if (input1.isNotEmpty()) input1 else getString(R.string.default_player1)
        player2Name = if (input2.isNotEmpty()) input2 else getString(R.string.default_player2)

        // 8a. Set the scores as 0 - 0
        player1Score = 0
        player2Score = 0
        scoreTextView.text = getString(R.string.score_format, player1Score, player2Score)

        // 8b. Disable editText1 and editText2 which contains player names.
        editText1.isEnabled = false
        editText2.isEnabled = false

        // 8c. Button1 text will be "<player1name> wins".
        button1.text = getString(R.string.player_wins_button, player1Name)

        // 8d. Button2 text will be "<player2name> wins".
        button2.text = getString(R.string.player_wins_button, player2Name)

        // 8e. Enable the button1 and button2.
        button1.isEnabled = true
        button2.isEnabled = true
    }

    private fun onPlayer1Clicked() {
        // 9. Increment corresponding player's point by 1.
        player1Score++
        scoreTextView.text = getString(R.string.score_format, player1Score, player2Score)

        // 9a. If one of the player reaches point 5, start ResultActivity.
        if (player1Score >= 5) {
            endGame(winnerName = player1Name)
        }
    }

    private fun onPlayer2Clicked() {
        // 9. Increment corresponding player's point by 1.
        player2Score++
        scoreTextView.text = getString(R.string.score_format, player1Score, player2Score)

        // 9a. If one of the player reaches point 5, start ResultActivity.
        if (player2Score >= 5) {
            endGame(winnerName = player2Name)
        }
    }

    private fun endGame(winnerName: String) {
        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra(EXTRA_WINNER_NAME, winnerName)
        }
        startActivity(intent)
    }

    private fun resetGame() {
        // 10a. Reset the inputs to begin a new game.
        editText1.text.clear()
        editText2.text.clear()
        editText1.isEnabled = true
        editText2.isEnabled = true

        button1.isEnabled = false
        button2.isEnabled = false
        button1.text = getString(R.string.button1_default)
        button2.text = getString(R.string.button2_default)

        player1Score = 0
        player2Score = 0
        scoreTextView.text = getString(R.string.score_initial)
    }

    companion object {
        const val EXTRA_WINNER_NAME = "extra_winner_name"
        const val EXTRA_RESET_GAME = "extra_reset_game"
    }
}
