package com.example.orderdeliver.data

import com.example.orderdeliver.data.models.FoodDataModel
import com.example.orderdeliver.data.models.FoodType

class FoodSource {
    private val foods = mutableListOf<FoodDataModel>()

    fun filterByType(foodType: FoodType): List<FoodDataModel>{
        if (foodType == FoodType.ALL)return foods
        return foods.filter { it.foodType == foodType }
    }
}