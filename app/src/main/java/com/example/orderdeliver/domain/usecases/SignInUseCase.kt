package com.example.orderdeliver.domain.usecases

import com.example.orderdeliver.data.validators.AuthValidator
import com.example.orderdeliver.domain.repositories.AuthServiceRepository
import com.example.orderdeliver.domain.repositories.AuthTokenRepository
import com.example.orderdeliver.domain.requests.SignInRequest
import com.example.orderdeliver.utils.showLog
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignInUseCase @Inject constructor(
    private val authServiceRepository: AuthServiceRepository,
    private val authTokenRepository: AuthTokenRepository

) {
    suspend operator fun invoke(signInRequest: SignInRequest){
        val signInResponse = authServiceRepository.signIn(signInRequest)
        authTokenRepository.saveTokens(signInResponse.refresh, signInResponse.access)
    }

}