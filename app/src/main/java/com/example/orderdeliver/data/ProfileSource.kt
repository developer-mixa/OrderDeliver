package com.example.orderdeliver.data

import com.example.orderdeliver.data.models.FoodDataModel
import kotlinx.coroutines.flow.MutableStateFlow

class ProfileSource {

    private val ordersStory = MutableStateFlow<List<String>>(emptyList())

    private val favorites = MutableStateFlow<List<FoodDataModel>>(emptyList())

    private val addresses = MutableStateFlow<List<String>>(emptyList())

    fun addFavorite(){}
    fun addOrderInStory(){}
    fun addAddress(){}


}