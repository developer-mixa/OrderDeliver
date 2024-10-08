package com.example.orderdeliver.domain.repositories

import com.example.orderdeliver.domain.requests.SignInRequest
import com.example.orderdeliver.domain.requests.SignUpRequest
import com.example.orderdeliver.domain.responses.SignInResponse
import com.example.orderdeliver.domain.responses.SignUpResponse

interface AuthServiceRepository {
    suspend fun signUp(signUpRequest: SignUpRequest) : SignUpResponse
    suspend fun signIn(signInRequest: SignInRequest) : SignInResponse
}