package com.example.orderdeliver.domain.repositories

import com.example.orderdeliver.domain.models.BasketModel
import com.example.orderdeliver.domain.models.PaymentModel
import com.example.orderdeliver.domain.Container

interface PaymasterRepository {
    suspend fun getPayment(baskets: List<BasketModel>, allBasketsCount: Int): Container<PaymentModel>
    suspend fun pay(baskets: List<BasketModel>): Container<String>
}