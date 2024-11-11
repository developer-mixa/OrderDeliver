package com.example.orderdeliver.domain.usecases

import com.example.orderdeliver.domain.repositories.AuthTokenRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserTokenUseCase @Inject constructor(
    private val authTokenRepository: AuthTokenRepository
) {

    operator fun invoke() : String{
        return authTokenRepository.getSuccessToken()
    }

}