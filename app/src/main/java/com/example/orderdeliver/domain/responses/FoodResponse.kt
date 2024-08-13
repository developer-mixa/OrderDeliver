package com.example.orderdeliver.domain.responses

import com.squareup.moshi.Json

data class FoodResponse(
    val categories: List<Any>,
    val description: String,
    val discount: DiscountResponse?,
    val id: String,
    @Json(name = "image_url") val imageUrl: String,
    val name: String,
    val price: Float,
    val restaurant: String,
    val weight: String
)

