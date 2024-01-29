package com.example.orderdeliver.di

import com.example.orderdeliver.data.repositories.DefaultPaymasterRepository
import com.example.orderdeliver.domain.repositories.PaymasterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PaymasterModule {

    @Binds
    abstract fun bindPaymasterRepository(
        defaultPaymasterRepository: DefaultPaymasterRepository
    ): PaymasterRepository


}