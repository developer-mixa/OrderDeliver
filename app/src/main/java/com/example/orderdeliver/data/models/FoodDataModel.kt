package com.example.orderdeliver.data.models

import androidx.annotation.DrawableRes

enum class FoodType { DRINK, FOOD, COMBO, SAUCE, ALL }

data class FoodDataModel(
    val id: Int,
    val name: String,
    val description: String,
    val weight: Float,
    val price: Int,
    @DrawableRes val imageResource: Int,
    val foodType: FoodType
)
