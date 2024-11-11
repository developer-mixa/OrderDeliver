package com.example.orderdeliver.data.repositories

import com.example.orderdeliver.data.base.BaseRetrofitSource
import com.example.orderdeliver.domain.api.profile.ProfileApi
import com.example.orderdeliver.domain.repositories.ProfileRepository
import com.example.orderdeliver.domain.responses.MeResponse
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepositoryImpl @Inject constructor(
    private val moshi: Moshi,
    private val retrofit: Retrofit,
) : BaseRetrofitSource(moshi), ProfileRepository {

    private val profileApi = retrofit.create(ProfileApi::class.java)

    override suspend fun getMe(authorization: String): MeResponse = wrapRetrofitExceptions {
        profileApi.getCurrentUser(authorization)
    }

}