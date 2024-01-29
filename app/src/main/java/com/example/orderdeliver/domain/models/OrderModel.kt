package com.example.orderdeliver.domain.models

interface OrderModel{
    val id: String
    val address: String
    val card: String
    val donePrice: Int
    val baskets: List<BasketModel>
    val date: String
}