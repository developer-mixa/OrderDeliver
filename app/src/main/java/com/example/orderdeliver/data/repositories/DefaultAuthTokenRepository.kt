package com.example.orderdeliver.data.repositories

import com.example.orderdeliver.domain.repositories.AuthTokenRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultAuthTokenRepository @Inject constructor(

) : AuthTokenRepository {
    override fun saveTokens(refreshToken: String, successToken: String) {

    }
}