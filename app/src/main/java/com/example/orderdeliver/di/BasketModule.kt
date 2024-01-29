package com.example.orderdeliver.di

import com.example.orderdeliver.data.repositories.DefaultBasketRepository
import com.example.orderdeliver.domain.repositories.BasketRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BasketModule {

    @Binds
    abstract fun bindBasketRepository(
        basketRepository: DefaultBasketRepository
    ): BasketRepository

}