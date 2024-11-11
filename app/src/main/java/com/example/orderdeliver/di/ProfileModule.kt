package com.example.orderdeliver.di

import com.example.orderdeliver.data.repositories.ProfileRepositoryImpl
import com.example.orderdeliver.domain.repositories.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ProfileModule {

    @Binds
    fun bindProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl) : ProfileRepository

}