package com.example.orderdeliver.domain.repositories

import kotlinx.coroutines.flow.Flow

interface AddressRepository {

    suspend fun writeAddress(address: String)

    suspend fun setCity()

    fun getAddress() : String

    fun getCountry() : String

    fun getCity() : Flow<String>

}