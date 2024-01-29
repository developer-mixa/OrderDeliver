package com.example.orderdeliver.domain.usecases

import com.example.orderdeliver.domain.repositories.AddressRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCurrentCityUseCase @Inject constructor(
    private val addressRepository: AddressRepository
) {

    fun listen() = addressRepository.getCity()

}