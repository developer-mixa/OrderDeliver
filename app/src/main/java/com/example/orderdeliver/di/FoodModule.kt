package com.example.orderdeliver.di

import com.example.orderdeliver.data.DefaultBasketRepository
import com.example.orderdeliver.data.TestFoodRepository
import com.example.orderdeliver.domain.BasketRepository
import com.example.orderdeliver.domain.FoodRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FoodModule {
    @Binds
    abstract fun bindFoodRepository(
        foodRepository: TestFoodRepository
    ): FoodRepository
}