package com.example.orderdeliver.di

import com.example.orderdeliver.data.DefaultBasketRepository
import com.example.orderdeliver.domain.BasketRepository
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