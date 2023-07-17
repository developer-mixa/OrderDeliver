package com.example.orderdeliver.presentation.menu

import com.example.navigation.BaseScreen
import com.example.navigation.BaseViewModel
import com.example.navigation.Navigator

class MenuViewModel(
    private val navigator: Navigator,
    screen: MenuFragment.Screen
) : BaseViewModel() {

    fun onAddToBasket(){

        navigator.launch(screen = AddToBasketFragment.Screen())
    }

}