package com.example.orderdeliver.data.models

data class PaymentModel(
    val id: String,
    val address: String,
    val card: String,
    val allCountSubjects: Int,
    val priceWithoutDiscount: Int,
    val discountSum: Int,
    val donePrice: Int
)
