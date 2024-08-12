package com.example.orderdeliver.domain.responses

data class FoodsResponse(
    val categories: List<Any>,
    val description: String,
    val discount: Any?,
    val id: String,
    val image_url: String,
    val name: String,
    val price: Float,
    val restaurant: String,
    val weight: String
)