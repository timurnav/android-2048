package com.developer.timurnav.fungames

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import com.developer.timurnav.fungames.Direction.*
import kotlinx.android.synthetic.main.activity_2048.*

class Game2048Activity : AppCompatActivity() {

    private lateinit var canvas: Canvas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2048)

        canvas = Canvas(4)

        canvas_layout.setOnTouchListener(
                OnSwipeTouchListener(
                        context = this@Game2048Activity,
                        eventsHolder = EventsHolder(
                                onSwipeTop = { swipe(UP) },
                                onSwipeBottom = { swipe(DOWN) },
                                onSwipeLeft = { swipe(LEFT) },
                                onSwipeRight = { swipe(RIGHT) }
                        )))
        updateView()
    }

    private fun swipe(direction: Direction) {
        if (canvas.isGameOver()) {
            startActivity(Intent(this@Game2048Activity, MainActivity::class.java))
            return
        }
        val moved = canvas.move(direction)
        if (moved) {
            canvas.createNewNumber(direction)
            updateView()
        }
    }

    private fun updateView() {
        setValue(cell00, 0, 0)
        setValue(cell01, 1, 0)
        setValue(cell02, 2, 0)
        setValue(cell03, 3, 0)
        setValue(cell04, 0, 1)
        setValue(cell05, 1, 1)
        setValue(cell06, 2, 1)
        setValue(cell07, 3, 1)
        setValue(cell08, 0, 2)
        setValue(cell09, 1, 2)
        setValue(cell10, 2, 2)
        setValue(cell11, 3, 2)
        setValue(cell12, 0, 3)
        setValue(cell13, 1, 3)
        setValue(cell14, 2, 3)
        setValue(cell15, 3, 3)

        if (canvas.isGameOver()) {
            Toast.makeText(this@Game2048Activity, "GAME OVER!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setValue(cell: TextView, column: Int, row: Int) {
        val number = canvas.numberAt(column, row)
        val textColor = resources.getColor(if (number < 8) R.color.color2 else R.color.color8)
        val backgroundColor = resources.getColor(when (number) {
            0 -> R.color.background0
            2 -> R.color.background2
            4 -> R.color.background4
            8 -> R.color.background8
            16 -> R.color.background16
            32 -> R.color.background32
            64 -> R.color.background64
            else -> R.color.background128
        })
        cell.textSize = if (number < 100) 50f else if (number < 1000) 40f else 30f
        cell.text = if (number == 0) "" else number.toString()
        cell.setTextColor(textColor)
        cell.setBackgroundColor(backgroundColor)
    }
}