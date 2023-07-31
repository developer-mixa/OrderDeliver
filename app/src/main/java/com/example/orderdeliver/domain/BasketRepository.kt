package com.example.orderdeliver.domain

import com.example.orderdeliver.data.models.BasketModel
import com.example.orderdeliver.data.models.FoodDataModel

interface BasketRepository {

    fun priceForSubject(basketModel: BasketModel): Int

    fun priceForAll(basketModels: List<BasketModel>): Int

    fun addBasket(foodDataModel: FoodDataModel)

    fun getBaskets(): List<BasketModel>?

}