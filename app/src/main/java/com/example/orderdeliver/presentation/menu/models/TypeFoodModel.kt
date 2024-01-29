package com.example.orderdeliver.presentation.menu.models

import com.example.orderdeliver.domain.models.FoodType

data class TypeFoodModel(
    val id: Int,
    val foodType: FoodType,
    val nameFoodType: String,
    val isActivated: Boolean = false
)
