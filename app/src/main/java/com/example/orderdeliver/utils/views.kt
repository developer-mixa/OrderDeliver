package com.example.orderdeliver.utils

import android.widget.Button

fun Button.markButtonDisable(enable: Boolean) {
    isEnabled = enable
    isClickable = enable

    alpha = if(enable)1f else 0.45f
}