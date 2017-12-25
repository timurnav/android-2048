package com.developer.timurnav.fungames

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.developer.timurnav.fungames.Direction.*
import kotlinx.android.synthetic.main.activity_2048.*

class Game2048Activity : AppCompatActivity() {

    private lateinit var canvas: Canvas
    private lateinit var tileFactory: TileFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2048)

        canvas = Canvas(4)

        tileFactory = game_playground.tileFactory

        canvas_layout.setOnTouchListener(OnSwipeTouchListener(
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
        val transaction = supportFragmentManager.beginTransaction()
        tiles.forEach { transaction.remove(it) }
        tiles.clear()
        canvas.iterateThrough { row, column, value ->
            val tile = tileFactory.createTile(value, row, column)
            tiles.add(tile)
            transaction.add(R.id.tiles_container, tile)
        }
        transaction.commit()
    }

    private val tiles = ArrayList<Tile>()
}