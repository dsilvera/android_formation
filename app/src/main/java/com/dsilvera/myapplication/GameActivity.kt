package com.dsilvera.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_game.*
import java.util.concurrent.TimeUnit


class GameActivity : AppCompatActivity() {
    private lateinit var operation: Operation
    private var score: Int = 0
    private var timer: CountDownTimer? = null
    private lateinit var adapter: OperationAdapter
    private var level: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        level = intent.getIntExtra(LEVEL_TAG, 0)

        generateCalculation()
        scoreTv.text = getString(R.string.score, score)

        timer = object : CountDownTimer(GAME_DURATION_IN_MILLISECOND, 1000L) {
            override fun onFinish() {
                val intent = Intent()
                intent.putExtra(RESULT_TAG, adapter.itemCount)
                setResult(RESULT_OK, intent)
                finish()
            }

            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - minutes * 60
                timerTv.text = getString(R.string.timeless, "$minutes:$seconds")
            }
        }.start()


        adapter = OperationAdapter()
        recyclerView.adapter = adapter
        (recyclerView.layoutManager as LinearLayoutManager).stackFromEnd = true
        validateButton.setOnClickListener {
            if (answer.text.toString() == operation.result().toString()) {
                score += if (level == 0) 1 else if (level == 1) 2 else 3
                adapter.add(operation)
                recyclerView.smoothScrollToPosition(adapter.itemCount)
                scoreTv.text = getString(R.string.score, score)
                generateCalculation()
                answer.text?.clear()
            } else {
                Toast.makeText(this, "Wrong answer", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun generateCalculation() {
        operation = Operation.generateOperation(level)
        mainTitle.text = operation.getOperationTemplate()
        mainTitle.setTextColor(
            ContextCompat.getColor(
                mainTitle.context,
                when {
                    operation.getTextColor() == OperationTextColor.BLUE -> android.R.color.holo_blue_dark
                    operation.getTextColor() == OperationTextColor.RED -> android.R.color.holo_red_dark
                    else -> android.R.color.holo_green_dark
                }
            )
        )
    }

    companion object {
        const val RESULT_TAG = "RESULT"
        const val LEVEL_TAG = "LEVEL"
        const val GAME_DURATION_IN_MILLISECOND = 60000L
    }

}