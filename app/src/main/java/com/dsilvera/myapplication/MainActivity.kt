package com.dsilvera.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var localeApi: LocaleApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        localeApi = LocaleApi(this)

        updateScores()

        startButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            val level = mainSelectorLevel.getValue()
            intent.putExtra(GameActivity.LEVEL_TAG, if (level == R.string.easy) 0 else if (level == R.string.medium) 1 else 2)
            startActivityForResult(intent, RESULT_CODE_GAME)
        }

        mainSelectorLevel.init(arrayListOf(R.string.easy, R.string.medium, R.string.hard))
    }

    private fun updateScores() {
        mainBestScore.text = "Best Score = ${localeApi.getBestScore()}"
        mainLastScore.text = "Latest Score = ${localeApi.getLatestScore()}"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_CODE_GAME) {
            if (resultCode == RESULT_OK) {
                val result = data?.getIntExtra(GameActivity.RESULT_TAG, 0) ?: 0

                localeApi.updateBestScore(result)
                localeApi.saveLatestScore(result)

                updateScores()
            }
        }
    }


    companion object {
        private const val RESULT_CODE_GAME = 111
    }
}