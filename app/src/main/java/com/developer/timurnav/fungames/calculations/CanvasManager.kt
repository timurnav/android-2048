package com.developer.timurnav.fungames.calculations

import com.developer.timurnav.fungames.domain.Direction
import com.developer.timurnav.fungames.domain.TileTo

class CanvasManager(size: Int) {

    private val lastIndex = size - 1
    private var data: List<List<TileTo?>> = List(size, { _ -> List(size, { _ -> null }) })

    private val movingManager = MovingManager(lastIndex)
    private val cellsManager = CellsManager(lastIndex)

    init {
        val directions = Direction.values().toList().shuffled()
        createNewNumber(directions[0])
        createNewNumber(directions[1])
    }

    fun isGameOver(): Boolean {
        return !(0..lastIndex).any { row ->
            (0..lastIndex).any { column ->
                val currentTile = data[row][column]
                currentTile == null ||
                        //next row is present and contain same value in same column
                        (row != lastIndex && currentTile.value() == data[row + 1][column]?.value()) ||
                        //next column is present and contain same value in same row
                        (column != lastIndex && currentTile.value() == data[row][column + 1]?.value())
            }
        }
    }

    fun move(direction: Direction): Boolean {
        val updated = when (direction) {
            Direction.UP -> movingManager.up(data)
            Direction.DOWN -> movingManager.down(data)
            Direction.LEFT -> movingManager.left(data)
            Direction.RIGHT -> movingManager.right(data)
        }
        val moved = updated != data
        data = updated
        return moved
    }

    fun createNewNumber(lastMove: Direction) {
        data = when (lastMove) {
            Direction.UP -> cellsManager.createAtBottom(data)
            Direction.DOWN -> cellsManager.createAtTop(data)
            Direction.LEFT -> cellsManager.createAtRight(data)
            Direction.RIGHT -> cellsManager.createAtLeft(data)
        }
    }

    /**
     * iterFunction(row, column, value)
     */
    fun iterateThrough(iterFunction: (Int, Int, TileTo) -> Unit) {
        (0..lastIndex).forEach { row ->
            (0..lastIndex).forEach { column ->
                data[row][column]?.let {
                    iterFunction(row, column, it)
                }
            }
        }
    }
}
