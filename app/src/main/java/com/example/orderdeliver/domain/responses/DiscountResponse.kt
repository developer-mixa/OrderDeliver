package com.example.orderdeliver.domain.responses

import com.squareup.moshi.Json

data class DiscountResponse(
    @Json(name = "end_date") val endDate: String,
    val id: String,
    @Json(name = "start_date") val startDate: String,
    val value: Float
)