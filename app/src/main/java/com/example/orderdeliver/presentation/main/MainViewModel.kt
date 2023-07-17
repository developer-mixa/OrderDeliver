package com.example.orderdeliver.presentation.main

import com.example.navigation.BaseViewModel
import com.example.navigation.Navigator
import com.example.orderdeliver.presentation.basket.BasketFragment
import com.example.orderdeliver.presentation.menu.MenuFragment
import com.example.orderdeliver.presentation.profile.ProfileFragment

class MainViewModel(
    private val navigator: Navigator,
    screen: MainFragment.Screen
): BaseViewModel() {

    fun openMenu()= navigator.launch(MenuFragment.Screen())

    fun openBasket() = navigator.launch(BasketFragment.Screen())

    fun openProfile() = navigator.launch(ProfileFragment.Screen())

}