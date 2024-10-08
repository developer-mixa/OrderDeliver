package com.example.orderdeliver.di

import com.example.orderdeliver.data.repositories.DefaultAuthServiceRepository
import com.example.orderdeliver.data.repositories.DefaultAuthTokenRepository
import com.example.orderdeliver.domain.repositories.AuthServiceRepository
import com.example.orderdeliver.domain.repositories.AuthTokenRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AuthModule {

    @Binds
    fun bindAuthRepository(defaultAuthRepository: DefaultAuthServiceRepository) : AuthServiceRepository

    @Binds
    fun bindAuthTokenRepository(defaultAuthTokenRepository: DefaultAuthTokenRepository) : AuthTokenRepository
}