package com.example.orderdeliver.presentation.models

import com.example.orderdeliver.domain.models.FoodDataModel

data class FoodListItem(
    val original: FoodDataModel,
    val priceText: String,
    val priceWithDiscountText: String? = null
){
    val id get() = original.id
    val name get() = original.name
    val description get() = original.description
    val imageResource get() = original.imageResource
}