package com.example.orderdeliver.data.repositories

import com.example.orderdeliver.data.base.BaseRetrofitSource
import com.example.orderdeliver.domain.api.profile.AuthApi
import com.example.orderdeliver.domain.repositories.AuthServiceRepository
import com.example.orderdeliver.domain.requests.SignInRequest
import com.example.orderdeliver.domain.requests.SignUpRequest
import com.example.orderdeliver.domain.responses.SignInResponse
import com.example.orderdeliver.domain.responses.SignUpResponse
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultAuthServiceRepository @Inject constructor(
    private val moshi: Moshi,
    private val retrofit: Retrofit,
): BaseRetrofitSource(moshi), AuthServiceRepository {

    private val authApi = retrofit.create(AuthApi::class.java)

    override suspend fun signUp(signUpRequest: SignUpRequest): SignUpResponse = wrapRetrofitExceptions {
        withContext(Dispatchers.IO){
            authApi.signUp(signUpRequest)
        }
    }

    override suspend fun signIn(signInRequest: SignInRequest): SignInResponse = wrapRetrofitExceptions{
        withContext(Dispatchers.IO){
            authApi.signIn(signInRequest)
        }
    }

}