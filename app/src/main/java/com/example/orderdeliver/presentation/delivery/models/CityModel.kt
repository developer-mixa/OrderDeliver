package com.example.orderdeliver.presentation.delivery.models

data class CityModel(
    val id: String,
    val title: String,
    val subTitle: String,
    val distance: Float? = null
)