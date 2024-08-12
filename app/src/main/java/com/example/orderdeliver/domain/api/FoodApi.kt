package com.example.orderdeliver.domain.api

import com.example.orderdeliver.domain.responses.FoodsResponse
import retrofit2.http.GET
import retrofit2.http.Header


interface FoodApi {

    @GET("rest/get_foods")
    suspend fun getFoods() : List<FoodsResponse>

}