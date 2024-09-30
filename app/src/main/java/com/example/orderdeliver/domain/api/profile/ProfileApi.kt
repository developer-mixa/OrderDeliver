package com.example.orderdeliver.domain.api.profile

import retrofit2.http.GET
import retrofit2.http.POST

interface ProfileApi {

    @POST("auth/users")
    fun signUp()

    @POST("auth/jwt/create")
    fun signIn()

    @GET("auth/users/me")
    fun getCurrentUser()

}