package com.example.car_gama_mad

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class GameOverActivity : AppCompatActivity() {

    private lateinit var scoreTextView: TextView
    private lateinit var restartButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        scoreTextView = findViewById(R.id.scoreTextView)
        restartButton = findViewById(R.id.restartButton)

        val score = intent.getIntExtra(EXTRA_SCORE, 0)
        scoreTextView.text = "Score: $score"

        restartButton.setOnClickListener {
            // Restart the game
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    companion object {
        const val EXTRA_SCORE = "extra_score"
    }
}