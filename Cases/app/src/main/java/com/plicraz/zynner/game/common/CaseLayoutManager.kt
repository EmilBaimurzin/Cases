package com.plicraz.zynner.game.common

import android.content.Context
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

open class CaseLayoutManager(private val context: Context?) : LinearLayoutManager(context) {
    var middleX = 0

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
        middleX = width / 2
    }

    override fun scrollHorizontallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        val scrolled = super.scrollHorizontallyBy(dy, recycler, state)

        if (scrolled != 0) {
            val firstItem = findFirstVisibleItemPosition()
            val lastItem = findLastVisibleItemPosition()
            val middleItem = (firstItem + lastItem) / 2
            val itemView = findViewByPosition(middleItem)
            val itemY = itemView?.let { getDecoratedTop(it) + itemView.width / 2 } ?: 0

            if (itemY == middleX) {
                return 0
            }
        }

        return scrolled
    }

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView,
        state: RecyclerView.State,
        position: Int
    ) {
        val smoothScroller: LinearSmoothScroller = object : LinearSmoothScroller(context) {
            private val SPEED = 100f
            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                return SPEED / displayMetrics.densityDpi
            }
        }
        smoothScroller.targetPosition = position
        startSmoothScroll(smoothScroller)
    }
}