package com.example.orderdeliver.domain.api

import com.example.orderdeliver.domain.requests.RequestBuy
import retrofit2.http.Body
import retrofit2.http.POST

interface ProductsApi {

    @POST("rest/buy/")
    suspend fun buy(@Body requestBuy: RequestBuy)

}