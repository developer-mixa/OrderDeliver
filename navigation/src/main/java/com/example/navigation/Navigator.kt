package com.example.navigation

import androidx.annotation.IdRes
import androidx.annotation.StringRes

interface Navigator {

    fun launch(screen: BaseScreen, addToBackStack: Boolean = false, aboveAll: Boolean = false)

    fun goBack(result: Any? = null)

    fun toast(@StringRes messageRes: Int)

    fun toast(messageString: String)

}