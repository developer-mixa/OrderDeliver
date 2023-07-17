package com.example.orderdeliver.data.models

enum class FoodType { DRINK, FOOD, COMBO, SAUCE, ALL }

data class FoodDataModel(
    val id: Int,
    val name: String,
    val description: String,
    val weight: Float,
    val price: Int,
    val imageResource: Int,
    val foodType: FoodType
)
