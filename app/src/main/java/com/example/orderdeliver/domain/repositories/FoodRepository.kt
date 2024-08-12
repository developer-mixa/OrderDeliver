package com.example.orderdeliver.domain.repositories

import androidx.paging.PagingData
import com.example.orderdeliver.domain.models.FoodDataModel
import com.example.orderdeliver.domain.models.FoodType
import com.example.orderdeliver.presentation.menu.models.TypeFoodModel
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    fun getPagedFoods(foodType: FoodType): Flow<PagingData<FoodDataModel>>

    fun getAllFoodTypes(): List<TypeFoodModel>

    fun setActivatedTypeFoodById(id: String): MutableList<TypeFoodModel>?

    suspend fun findFoodById(id: String) : FoodDataModel

    suspend fun reduceFood(id: String, reduceCount: Int)
}