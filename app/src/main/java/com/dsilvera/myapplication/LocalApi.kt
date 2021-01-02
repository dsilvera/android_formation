package com.dsilvera.myapplication

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

class LocaleApi(activity: AppCompatActivity) {
    private var sharedPreference: SharedPreferences = activity.getPreferences(Context.MODE_PRIVATE)

    fun updateBestScore(score: Int) {
        val bestScore = getBestScore()
        if (score > bestScore) {
            sharedPreference.edit().putInt(BEST_SCORE_KEY, score).apply()
        }
    }

    fun getBestScore() = sharedPreference.getInt(BEST_SCORE_KEY, 0)

    fun saveLatestScore(score: Int) {
        sharedPreference.edit().putInt(LATEST_SCORE_KEY, score).apply()
    }

    fun getLatestScore() = sharedPreference.getInt(LATEST_SCORE_KEY, 0)

    companion object {
        const val BEST_SCORE_KEY = "BestScore"
        const val LATEST_SCORE_KEY = "LatestScore"
    }
}