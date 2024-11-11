package com.example.orderdeliver.domain.repositories

import com.example.orderdeliver.domain.responses.MeResponse
import retrofit2.http.Header

interface ProfileRepository {
    suspend fun getMe(authorization: String) : MeResponse
}