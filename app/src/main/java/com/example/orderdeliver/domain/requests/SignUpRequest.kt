package com.example.orderdeliver.domain.requests

import com.squareup.moshi.Json

data class SignUpRequest(
    val username: String,
    val password: String,
    @Json(name = "re_password") val repeatPassword: String,
    val phone: String?,
    val email: String?
)