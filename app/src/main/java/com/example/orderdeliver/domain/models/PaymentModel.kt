package com.example.orderdeliver.domain.models

data class PaymentModel(
    override val id: String,
    override val address: String,
    override val card: String,
    val allCountSubjects: Int,
    val priceWithoutDiscount: Float,
    val discountSum: Float,
    override val donePrice: Float,
    override val baskets: List<BasketModel>,
    override val date: String
) : OrderModel
