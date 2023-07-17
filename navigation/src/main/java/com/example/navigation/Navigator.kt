package com.example.navigation

import androidx.annotation.StringRes

interface Navigator {

    fun launch(screen: BaseScreen)

    fun goBack(result: Any? = null)

    fun toast(@StringRes messageRes: Int)

    fun toast(messageString: String)

}