package com.example.orderdeliver.domain.usecases

import com.example.orderdeliver.data.validators.AuthValidator
import com.example.orderdeliver.domain.repositories.AuthServiceRepository
import com.example.orderdeliver.domain.requests.SignUpRequest
import com.example.orderdeliver.utils.showLog
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignUpUseCase @Inject constructor(
    private val authServiceRepository: AuthServiceRepository,
    private val authValidator: AuthValidator
) {
    suspend operator fun invoke(signUpRequest: SignUpRequest){

        showLog("invoke")

        signUpRequest.phone?.let { phone->
            if(!authValidator.validNumber(phone)){
                throw IllegalStateException("Enter a valid number!")
            }
        }

        signUpRequest.email?.let { email->
            if(!authValidator.validEmail(email)){
                throw IllegalStateException("Enter a valid email!")
            }
        }

        authServiceRepository.signUp(signUpRequest)
    }
}