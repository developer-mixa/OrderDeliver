package com.example.orderdeliver.data.sources

import com.example.orderdeliver.domain.Container
import com.example.orderdeliver.domain.models.OrderModel
import com.example.orderdeliver.domain.sources.OrderStorySource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultOrderStorySource @Inject constructor(): OrderStorySource {
    private val orders: MutableList<OrderModel> = mutableListOf()
    override fun getOrders(): List<OrderModel> {
        return orders
    }

    override fun add(order: OrderModel){
        orders.add(order)
    }
}