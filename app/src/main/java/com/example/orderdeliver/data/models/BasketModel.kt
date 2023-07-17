package com.example.orderdeliver.data.models

data class BasketModel(
    val id: Int,
    val foodDataModel: FoodDataModel,
    val count: Int
)
