package com.plicraz.zynner.game.common

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper

class IntervalSnapHelper(private val interval: Int) : SnapHelper() {

    override fun calculateDistanceToFinalSnap(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View
    ): IntArray {
        val out = IntArray(2)
        out[0] = getHorizontalDistance(layoutManager, targetView)
        out[1] = getVerticalDistance(layoutManager, targetView)
        return out
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? {
        return getSnapView(layoutManager as GridLayoutManager)
    }

    private fun getSnapView(layoutManager: GridLayoutManager): View? {
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

        var snapView: View? = null
        var snapDistance = Int.MAX_VALUE

        for (i in firstVisibleItemPosition..lastVisibleItemPosition) {
            val child = layoutManager.findViewByPosition(i)
            if (child != null) {
                val distance = getHorizontalDistance(layoutManager, child)
                if (distance < snapDistance) {
                    snapDistance = distance
                    snapView = child
                }
            }
        }

        return snapView
    }

    override fun findTargetSnapPosition(layoutManager: RecyclerView.LayoutManager, velocityX: Int, velocityY: Int): Int {
        if (layoutManager !is LinearLayoutManager) {
            return RecyclerView.NO_POSITION
        }

        val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
        val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
        if (firstVisiblePosition == RecyclerView.NO_POSITION || lastVisiblePosition == RecyclerView.NO_POSITION) {
            return RecyclerView.NO_POSITION
        }

        val childCount = layoutManager.childCount
        val width = layoutManager.width
        val height = layoutManager.height

        val visibleRange = lastVisiblePosition - firstVisiblePosition
        val visibleThreshold = (visibleRange + 1) / 2
        var closestChild: View? = null
        var closestDistance = Int.MAX_VALUE

        for (i in 0 until childCount) {
            val child = layoutManager.getChildAt(i)
            if (child == null) {
                continue
            }

            val position = layoutManager.getPosition(child)
            if (position < firstVisiblePosition || position > lastVisiblePosition) {
                continue
            }

            val distance = if (layoutManager.canScrollHorizontally()) {
                getHorizontalDistance(layoutManager, child)
            } else {
                getVerticalDistance(layoutManager, child)
            }

            if (Math.abs(distance) < Math.abs(closestDistance)) {
                closestChild = child
                closestDistance = distance
            }
        }

        if (closestChild == null) {
            return RecyclerView.NO_POSITION
        }

        val closestPosition = layoutManager.getPosition(closestChild)
        val snapToInterval = interval / 2
        return if (closestDistance < 0 && Math.abs(closestDistance) >= snapToInterval) {
            closestPosition + 1
        } else if (closestDistance > 0 && closestDistance >= snapToInterval) {
            closestPosition + 1
        } else if (closestDistance < 0 && Math.abs(closestDistance) < snapToInterval) {
            closestPosition
        } else {
            closestPosition
        }
    }

    private fun getHorizontalDistance(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View
    ): Int {
        val center = layoutManager.width / 2
        val targetCenter = targetView.left + targetView.width / 2
        val distance = targetCenter - center
        return if (Math.abs(distance) < interval / 2) {
            0
        } else if (distance > 0) {
            distance - interval / 2
        } else {
            distance + interval / 2
        }
    }

    private fun getVerticalDistance(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View
    ): Int {
        val center = layoutManager.height / 2
        val targetCenter = targetView.top + targetView.height / 2
        val distance = targetCenter - center
        return if (Math.abs(distance) < interval / 2) {
            0
        } else if (distance > 0) {
            distance - interval / 2
        } else {
            distance + interval / 2
        }
    }
}