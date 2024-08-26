package com.example.orderdeliver.presentation.models

data class PaymentItem(
    val address: String,
    val countOrdersText: String,
    val withoutDiscountPriceText: String,
    val discountSumText: String,
    val finalPriceText: String
)