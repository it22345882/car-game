package com.example.car_gama_mad

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity(), GameTask {
    lateinit var rootLayout: LinearLayout
    lateinit var startBtn: Button
    lateinit var highScoreBtn: Button
    lateinit var mGameView: GameView
    lateinit var score: TextView
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("game_data", Context.MODE_PRIVATE)

        // Find views
        startBtn = findViewById(R.id.startBtn)
        highScoreBtn = findViewById(R.id.highScoreBtn)
        rootLayout = findViewById(R.id.rootLayout)
        score = findViewById(R.id.score)
        mGameView = GameView(this, this)

        startBtn.setOnClickListener {
            mGameView.reset()
            mGameView.setBackgroundResource(R.drawable.background)
            rootLayout.addView(mGameView)
            startBtn.visibility = View.GONE
            highScoreBtn.visibility = View.GONE
            score.visibility = View.GONE
        }

        highScoreBtn.setOnClickListener {
            val highScore = sharedPreferences.getInt(HIGH_SCORE_KEY, 0)
            score.text = "High Score: $highScore"
            rootLayout.removeView(mGameView)
            startBtn.visibility = View.VISIBLE
            highScoreBtn.visibility = View.VISIBLE
            score.visibility = View.VISIBLE
        }
    }

    override fun closeGame(mScore: Int) {
        // Update score TextView with the current score obtained
        score.text = "Score: $mScore"

        // Save score to SharedPreferences
        val currentHighScore = sharedPreferences.getInt(HIGH_SCORE_KEY, 0)
        if (mScore > currentHighScore) {
            sharedPreferences.edit().putInt(HIGH_SCORE_KEY, mScore).apply()
        }

        // Inflate the game over layout
        val gameOverLayout = layoutInflater.inflate(R.layout.activity_game_over, null)

        // Find the score TextView and set its text
        val gameOverScoreTextView = gameOverLayout.findViewById<TextView>(R.id.scoreTextView)
        gameOverScoreTextView.text = "Score: $mScore"

        // Find the restart Button and set its click listener
        val gameOverRestartButton = gameOverLayout.findViewById<Button>(R.id.restartButton)
        gameOverRestartButton.setOnClickListener {
            // Reset the game
            mGameView.reset()

            // Remove the game over layout and show the main layout
            rootLayout.removeView(gameOverLayout)
            startBtn.visibility = View.VISIBLE
            highScoreBtn.visibility = View.VISIBLE
            score.visibility = View.VISIBLE

            // Start the game
            mGameView.start()
        }

        // Remove the game view and show the game over layout
        rootLayout.removeView(mGameView)
        rootLayout.addView(gameOverLayout)

        startBtn.visibility = View.GONE
        highScoreBtn.visibility = View.GONE
        score.visibility = View.GONE
    }

    companion object {
        private const val HIGH_SCORE_KEY = "high_score"
    }
}