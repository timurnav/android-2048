package com.developer.timurnav.fungames.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.developer.timurnav.fungames.R
import com.developer.timurnav.fungames.calculations.CanvasManager
import com.developer.timurnav.fungames.domain.Direction
import com.developer.timurnav.fungames.domain.Direction.*
import com.developer.timurnav.fungames.gameboard.Tile
import com.developer.timurnav.fungames.gameboard.TileFactory
import kotlinx.android.synthetic.main.activity_2048.*

class Game2048Activity : AppCompatActivity() {

    private lateinit var canvasManager: CanvasManager
    private lateinit var tileFactory: TileFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2048)

        canvasManager = CanvasManager(4)

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
        if (canvasManager.isGameOver()) {
            startActivity(Intent(this@Game2048Activity, MainActivity::class.java))
            finish()
            return
        }

        val moved = canvasManager.move(direction)
        if (moved) {
            canvasManager.createNewNumber(direction)
            updateView()
        }
    }

    private fun updateView() {
        val toBeRemoved = ArrayList<Tile>()
        val transaction = supportFragmentManager.beginTransaction()
        canvasManager.iterateThrough { row, column, value ->
            if (tiles.containsKey(value.id)) {
                val tile = tiles[value.id]!!
                value.ifUpgraded {
                    tileFactory.upgrade(tile, value.value())
                    toBeRemoved.add(tiles.remove(it)!!)
                }
                tile.move(row, column)
            } else {
                val newTile = tileFactory.createTile(value.value(), row, column)
                transaction.add(R.id.tiles_container, newTile)
                tiles.put(value.id, newTile)
            }
        }
        toBeRemoved.forEach {
            transaction.remove(it)
        }

        transaction.commit()
    }

    private val tiles = HashMap<Int, Tile>()
}