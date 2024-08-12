package com.example.orderdeliver.domain.interceptors

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val builder: Request.Builder =
            originalRequest.newBuilder().header("Api-key", "django-insecure-1!2w9ig*j^s0(q6m(u+2s-@rwhf%^ykjxx33r-zjz3jy+l^ke9")
        val newRequest: Request = builder.build()
        return chain.proceed(newRequest)
    }
}