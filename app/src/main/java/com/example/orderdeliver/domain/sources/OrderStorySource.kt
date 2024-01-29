package com.example.orderdeliver.domain.sources

import com.example.orderdeliver.domain.models.OrderModel
import com.example.orderdeliver.domain.Container

interface OrderStorySource {

    fun getOrders() : List<OrderModel>

    fun add(order: OrderModel)
}