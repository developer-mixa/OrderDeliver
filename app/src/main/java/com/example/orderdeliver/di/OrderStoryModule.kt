package com.example.orderdeliver.di

import com.example.orderdeliver.data.sources.DefaultOrderStorySource
import com.example.orderdeliver.domain.sources.OrderStorySource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class OrderStoryModule {

    @Binds
    abstract fun bindOrderStory(defaultOrderStorySource: DefaultOrderStorySource): OrderStorySource

}