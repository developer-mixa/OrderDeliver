package com.example.orderdeliver.domain.interceptors

import com.example.orderdeliver.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val builder: Request.Builder =
            originalRequest.newBuilder()
                .header(API_KEY, BuildConfig.BACKEND_API_KEY)
        val newRequest: Request = builder.build()
        return chain.proceed(newRequest)
    }

    private companion object{
        const val API_KEY = "Api-key"
    }

}