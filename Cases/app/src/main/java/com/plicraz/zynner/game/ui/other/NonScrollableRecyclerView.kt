package com.plicraz.zynner.game.ui.other

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent

import androidx.recyclerview.widget.RecyclerView


class NonScrollableRecyclerView(context: Context, attr: AttributeSet) : RecyclerView(context, attr) {

    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        return false
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        return false
    }
}