package com.example.orderdeliver.utils

import android.view.View

fun View.getHeightIfGone(): Int {
    val view = this

    val widthSpec =
        View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY)
    val heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    view.measure(widthSpec, heightSpec)
    return view.measuredHeight
}