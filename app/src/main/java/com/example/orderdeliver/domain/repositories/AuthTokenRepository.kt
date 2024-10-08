package com.example.orderdeliver.domain.repositories

interface AuthTokenRepository {
    fun saveTokens(refreshToken: String, successToken: String)
}