package com.developer.timurnav.fungames.gameboard

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.developer.timurnav.fungames.R


class Tile : Fragment() {

    private var value = -1
    private lateinit var location: Location
    private lateinit var dimensions: Dimensions
    private lateinit var viewState: ViewState

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.tile_view, container, false) as TextView
        doSetUpView(view)
        doMove(view)
        return view
    }

    fun move(row: Int, column: Int) {
        location = Location(row, column)
        doMove(view!! as TextView)
    }

    fun update(number: Int, newViewState: ViewState) {
        this.value = number
        this.viewState = newViewState
        doSetUpView(view!! as TextView)
    }

    private fun doSetUpView(view: TextView) {
        view.textSize = viewState.textSize
        view.text = value.toString()
        view.setTextColor(resources.getColor(viewState.color))

        val shape = GradientDrawable()
        shape.cornerRadius = dimensions.round
        shape.setColor(resources.getColor(viewState.background))

        view.background = shape
    }

    private fun doMove(view: TextView) {
        val layoutParams = RelativeLayout.LayoutParams(dimensions.size, dimensions.size)
        layoutParams.topMargin = location.row * (dimensions.size + dimensions.margin)
        layoutParams.leftMargin = location.column * (dimensions.size + dimensions.margin)
        view.layoutParams = layoutParams
    }

    companion object {
        fun create(number: Int,
                   row: Int,
                   column: Int,
                   tileSize: Int,
                   tileRound: Float,
                   tileMargin: Int,
                   viewState: ViewState): Tile {
            val tile = Tile()
            tile.value = number
            tile.dimensions = Dimensions(tileSize, tileRound, tileMargin)
            tile.location = Location(row, column)
            tile.viewState = viewState
            return tile
        }

    }

    data class Location(val row: Int, val column: Int)

    data class Dimensions(val size: Int, val round: Float, val margin: Int)

    data class ViewState(val textSize: Float, val color: Int, val background: Int)

    override fun toString(): String {
        return "Tile(value=$value, location=$location)"
    }
}

