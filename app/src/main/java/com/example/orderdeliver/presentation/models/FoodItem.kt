package com.example.orderdeliver.presentation.models

import androidx.annotation.DrawableRes

data class FoodItem(
    val name: String,
    val description: String,
    @DrawableRes val imageResource: Int,
    val finalPrice: Float
)