package com.example.orderdeliver.domain.api.profile

import com.example.orderdeliver.domain.responses.MeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ProfileApi {

    @GET("auth/users/me")
    suspend fun getCurrentUser(@Header("Authorization") authorization: String) : MeResponse

}