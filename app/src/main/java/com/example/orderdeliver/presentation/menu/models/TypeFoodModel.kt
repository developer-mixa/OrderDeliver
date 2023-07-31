package com.example.orderdeliver.presentation.menu.models

import com.example.orderdeliver.data.models.FoodType

data class TypeFoodModel(
    val id: Int,
    val foodType: FoodType,
    val nameFoodType: String,
    val isActivated: Boolean = false
)
