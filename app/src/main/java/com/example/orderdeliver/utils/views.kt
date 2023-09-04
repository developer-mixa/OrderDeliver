package com.example.orderdeliver.utils

import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import com.example.orderdeliver.R

fun View.getHeightIfGone(): Int {
    val view = this

    val widthSpec =
        View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY)
    val heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    view.measure(widthSpec, heightSpec)
    return view.measuredHeight
}

fun Button.markButtonDisable(enable: Boolean) {
    isEnabled = enable
    isClickable = enable

    alpha = if(enable)1f else 0.45f
}