package com.example.orderdeliver.domain.api

import com.example.orderdeliver.domain.responses.FoodCategoryResponse
import com.example.orderdeliver.domain.responses.FoodResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface FoodApi {

    @GET("rest/get_foods")
    suspend fun getFoods(@Query("categories") categories: List<String>? = null) : List<FoodResponse>

    @GET("rest/get_food_categories")
    suspend fun getCategories() : List<FoodCategoryResponse>

}