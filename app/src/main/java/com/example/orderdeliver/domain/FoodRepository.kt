package com.example.orderdeliver.domain

import androidx.paging.PagingData
import com.example.orderdeliver.data.models.FoodDataModel
import com.example.orderdeliver.data.models.FoodType
import com.example.orderdeliver.presentation.menu.models.TypeFoodModel
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    fun getPagedFoods(foodType: FoodType): Flow<PagingData<FoodDataModel>>

    fun getAllFoodTypes(): List<TypeFoodModel>

    fun setActivatedTypeFoodById(id: Int): MutableList<TypeFoodModel>?

}