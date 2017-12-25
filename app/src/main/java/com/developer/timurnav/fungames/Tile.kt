package com.developer.timurnav.fungames

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView


class Tile : Fragment() {

    private lateinit var propertyHolder: PropertyHolder

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.tile_view, container, false) as TextView
        view.text = propertyHolder.text
        view.textSize = propertyHolder.textSize
        view.setTextColor(resources.getColor(propertyHolder.color))
        val size = propertyHolder.tileSize
        val layoutParams = FrameLayout.LayoutParams(size, size)

        val shape = GradientDrawable()
        shape.cornerRadius = propertyHolder.tileRound
        shape.setColor(resources.getColor(propertyHolder.background))

        layoutParams.topMargin = propertyHolder.row * (propertyHolder.tileSize + propertyHolder.tileMargin)
        layoutParams.leftMargin = propertyHolder.column * (propertyHolder.tileSize + propertyHolder.tileMargin)
        view.layoutParams = layoutParams

        view.background = shape

        return view
    }

    companion object {
        fun createTile(
                text: String,
                row: Int,
                column: Int,
                textSize: Float,
                color: Int,
                background: Int,
                tileSize: Int,
                tileRound: Float,
                tileMargin: Int): Tile {
            val tile = Tile()
            tile.propertyHolder =
                    PropertyHolder(text, row, column, textSize, color, background, tileSize, tileRound, tileMargin)
            return tile
        }

        data class PropertyHolder(
                val text: String,
                val row: Int,
                val column: Int,
                val textSize: Float,
                val color: Int,
                val background: Int,
                val tileSize: Int,
                val tileRound: Float,
                val tileMargin: Int
        )
    }
}

