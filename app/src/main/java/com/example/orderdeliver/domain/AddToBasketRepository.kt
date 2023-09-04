package com.example.orderdeliver.domain

import com.example.orderdeliver.data.models.FoodDataModel

interface AddToBasketRepository {

    fun getPrice(price: Int,discount: Int?): Int


}