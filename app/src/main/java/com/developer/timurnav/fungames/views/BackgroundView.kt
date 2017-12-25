package com.developer.timurnav.fungames.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
import android.view.View
import com.developer.timurnav.fungames.R


class BackgroundView(
        context: Context,
        attrs: AttributeSet
) : View(context, attrs) {

    private val composite: Drawable

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.BackgroundView, 0, 0)
        val tilesNumber = attributes.getInt(R.styleable.BackgroundView_tiles_number, 0)
        val tileMargin = attributes.getDimensionPixelSize(R.styleable.BackgroundView_tile_margin, 0)
        val tileRound = attributes.getDimension(R.styleable.BackgroundView_tile_round, 0f)
        val tileSize = attributes.getDimensionPixelSize(R.styleable.BackgroundView_tile_size, 0)
        val frameColor = attributes.getColor(R.styleable.BackgroundView_frame_color, 0)

        val rounds = FloatArray(8, { _ -> tileRound })
        val array = (0 until tilesNumber).flatMap { row ->
            val rowOffset = (tileSize + tileMargin) * row
            (0 until tilesNumber).map { column ->
                val columnOffset = (tileSize + tileMargin) * column
                val rectangle = ShapeDrawable(RoundRectShape(rounds, null, null))
                rectangle.paint.color = frameColor
                rectangle.setBounds(
                        columnOffset,
                        rowOffset,
                        columnOffset + tileSize,
                        rowOffset + tileSize
                )
                return@map rectangle
            }
        }
                .toTypedArray()

        composite = LayerDrawable(array)

        attributes.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        composite.draw(canvas)
    }
}