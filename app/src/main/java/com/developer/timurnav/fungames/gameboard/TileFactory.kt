package com.developer.timurnav.fungames.gameboard

import com.developer.timurnav.fungames.R

class TileFactory(
        private val tileSize: Int,
        private val tileRound: Float,
        private val tileMargin: Int,
        private val tilesNumber: Int) {

    fun createTile(number: Int, row: Int, column: Int): Tile {
        return Tile.create(number, row, column, tileSize, tileRound, tileMargin, getViewState(number))
    }

    fun upgrade(tile: Tile, number: Int) {
        tile.update(number, getViewState(number))
    }

    private fun getViewState(number: Int): Tile.ViewState {
        val background = getBackgroundColor(number)
        val color = if (number < 8) R.color.color2 else R.color.color8
        val textSize = if (number < 100) 50f else if (number < 1000) 40f else 30f
        val viewState = Tile.ViewState(textSize, color, background)
        return viewState
    }

    private fun getBackgroundColor(number: Int): Int {
        return when (number) {
            0 -> R.color.background0
            2 -> R.color.background2
            4 -> R.color.background4
            8 -> R.color.background8
            16 -> R.color.background16
            32 -> R.color.background32
            64 -> R.color.background64
            else -> R.color.background128
        }
    }
}
