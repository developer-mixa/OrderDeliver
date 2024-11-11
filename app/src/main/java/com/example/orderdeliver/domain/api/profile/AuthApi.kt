package com.example.orderdeliver.domain.api.profile

import com.example.orderdeliver.domain.requests.SignInRequest
import com.example.orderdeliver.domain.requests.SignUpRequest
import com.example.orderdeliver.domain.responses.SignInResponse
import com.example.orderdeliver.domain.responses.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/users")
    suspend fun signUp(@Body signUpRequest: SignUpRequest) : SignUpResponse

    @POST("auth/jwt/create")
    suspend fun signIn(@Body signInRequest: SignInRequest) : SignInResponse
}