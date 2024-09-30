package com.example.orderdeliver.presentation.delivery

import androidx.lifecycle.ViewModel
import com.example.orderdeliver.presentation.plugins.plugins.NavigatorPlugin
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaceDeliveryViewModel @Inject constructor(
    private val navigator: NavigatorPlugin
) : ViewModel() {

    fun exit() = navigator.goBack()

    fun chooseDeliver(){
        navigator.launch(screen = ChooseAddressFragment.Screen(), addToBackStack = true, aboveAll = true)
    }

    fun chooseRestaurant(){

    }

}