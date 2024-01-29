package com.example.orderdeliver.di

import com.example.orderdeliver.data.repositories.DefaultFoodRepository
import com.example.orderdeliver.domain.repositories.FoodRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FoodModule {
    @Binds
    abstract fun bindFoodRepository(
        foodRepository: DefaultFoodRepository
    ): FoodRepository
}