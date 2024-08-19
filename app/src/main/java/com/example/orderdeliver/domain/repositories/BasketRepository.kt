package com.example.orderdeliver.domain.repositories

import com.example.orderdeliver.domain.models.BasketModel
import com.example.orderdeliver.domain.models.FoodDataModel
import kotlinx.coroutines.flow.Flow

interface BasketRepository {

    suspend fun addBasket(foodDataModel: FoodDataModel)

    suspend fun removeBasket(id: String)

    suspend fun minusOneBasket(id: String)

    fun listenBaskets(): Flow<List<BasketModel>>

    fun listenAllCount(): Flow<Int>

    suspend fun clear()

}