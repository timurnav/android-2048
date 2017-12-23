package com.developer.timurnav.fungames

import android.annotation.SuppressLint
import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

class OnSwipeTouchListener(context: Context, eventsHolder: EventsHolder) : View.OnTouchListener {

    private val gestureDetector: GestureDetector = GestureDetector(
            context,
            GestureListener(eventsHolder, SWIPE_THRESHOLD, SWIPE_VELOCITY_THRESHOLD)
    )

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    companion object {
        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100
    }
}

private class GestureListener(
        private val eventsHolder: EventsHolder,
        private val swipeThreshold: Int,
        private val swipeVelocityThreshold: Int
) : GestureDetector.SimpleOnGestureListener() {

    override fun onDown(e: MotionEvent): Boolean {
        return true
    }

    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        var result = false
        try {
            val diffY = e2.y - e1.y
            val diffX = e2.x - e1.x
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > swipeThreshold && Math.abs(velocityX) > swipeVelocityThreshold) {
                    if (diffX > 0) {
                        eventsHolder.onSwipeRight()
                    } else {
                        eventsHolder.onSwipeLeft()
                    }
                    result = true
                }
            } else if (Math.abs(diffY) > swipeThreshold && Math.abs(velocityY) > swipeVelocityThreshold) {
                if (diffY > 0) {
                    eventsHolder.onSwipeBottom()
                } else {
                    eventsHolder.onSwipeTop()
                }
                result = true
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }

        return result
    }
}

data class EventsHolder(
        val onSwipeTop: () -> Unit = {},
        val onSwipeBottom: () -> Unit = {},
        val onSwipeLeft: () -> Unit = {},
        val onSwipeRight: () -> Unit = {}
)