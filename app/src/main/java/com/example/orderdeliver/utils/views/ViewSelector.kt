package com.example.orderdeliver.utils

import android.content.Context
import android.view.View

interface ViewSelector {

    val context : Context

    val chooseColor: Int

    val turnedOffColor: Int

    fun <T: View>chooseView(view: T)

}