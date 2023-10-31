package com.example.orderdeliver.presentation.delivery.models

data class CityModel(
    val uri: String,
    val title: String,
    val subTitle: String,
    val distance: Float? = null
)