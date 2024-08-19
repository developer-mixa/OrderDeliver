package com.example.orderdeliver.domain.repositories

import androidx.paging.PagingData
import com.example.orderdeliver.domain.models.FoodDataModel
import com.example.orderdeliver.presentation.menu.models.TypeFoodModel
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    fun getPagedFoods(foodTypeId: String): Flow<PagingData<FoodDataModel>>

    suspend fun getAllFoodTypes(): List<TypeFoodModel>

    fun setActivatedTypeFoodById(id: String): List<TypeFoodModel>?

    suspend fun findFoodById(id: String) : FoodDataModel

}