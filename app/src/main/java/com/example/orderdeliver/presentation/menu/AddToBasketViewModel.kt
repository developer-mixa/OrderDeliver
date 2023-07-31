package com.example.orderdeliver.presentation.menu

import androidx.lifecycle.MutableLiveData
import com.example.navigation.BaseViewModel
import com.example.navigation.Event
import com.example.navigation.Navigator
import com.example.orderdeliver.data.models.FoodDataModel
import com.example.orderdeliver.share
import com.example.orderdeliver.showLog

class AddToBasketViewModel(
    private val navigator: Navigator,
    screen: AddToBasketFragment.Screen
): BaseViewModel() {

    private val _initialFoodModelEvent = MutableLiveData<Event<FoodDataModel>>()
    val initialFoodModelEvent = _initialFoodModelEvent.share()

    init {
        showLog(screen.foodDataModel.name)
        _initialFoodModelEvent.value = Event(screen.foodDataModel)
    }

    fun back() = navigator.goBack()

}