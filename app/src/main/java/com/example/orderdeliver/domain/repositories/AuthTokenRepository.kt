package com.example.orderdeliver.domain.repositories

interface AuthTokenRepository {
    fun saveTokens(refreshToken: String, successToken: String)
    fun getRefreshToken() : String
    fun getSuccessToken() : String
}