package com.example.orderdeliver.di

import com.example.orderdeliver.data.DefaultAddressRepository
import com.example.orderdeliver.domain.AddressRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AddressModule {

    @Binds
    abstract fun bindAddressRepository(defaultAddressRepository: DefaultAddressRepository) : AddressRepository

}