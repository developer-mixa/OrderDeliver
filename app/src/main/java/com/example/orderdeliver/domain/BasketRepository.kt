package com.example.orderdeliver.domain

import com.example.orderdeliver.data.models.BasketModel
import com.example.orderdeliver.data.models.FoodDataModel
import com.example.orderdeliver.data.models.PaymentModel
import kotlinx.coroutines.flow.Flow

interface BasketRepository {

    fun priceForSubject(basketModel: BasketModel): Int

    fun priceForAll(basketModels: List<BasketModel>): Int

    suspend fun addBasket(foodDataModel: FoodDataModel)

    suspend fun removeBasket(id: Int)

    suspend fun minusOneBasket(id: Int)

    fun listenBaskets(): Flow<List<BasketModel>>

    fun listenAllCount(): Flow<Int>

    fun priceForAllWithoutDiscount(basketModels: List<BasketModel>): Int

    fun getDiscountCount(basketModels: List<BasketModel>): Int

    suspend fun getPayment(): Container<PaymentModel>
}