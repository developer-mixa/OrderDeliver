package com.example.orderdeliver.domain.models

data class PaymentModel(
    override val id: String,
    override val address: String,
    override val card: String,
    val allCountSubjects: Int,
    val priceWithoutDiscount: Int,
    val discountSum: Int,
    override val donePrice: Int,
    override val baskets: List<BasketModel>,
    override val date: String
) : OrderModel
