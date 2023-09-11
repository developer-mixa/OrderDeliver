package com.example.orderdeliver.presentation.delivery

import com.example.navigation.BaseScreen
import com.example.navigation.BaseViewModel
import com.example.navigation.Navigator

class PlaceDeliveryViewModel(
    private val navigator: Navigator,
    screen: PlaceDeliveryFragment.Screen,
) : BaseViewModel() {

    fun exit() = navigator.goBack()

    fun chooseDeliver(){
        navigator.launch(screen = ChooseAddressFragment.Screen(), addToBackStack = true, aboveAll = true)
    }

    fun chooseRestaurant(){

    }

}