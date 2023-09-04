package com.example.orderdeliver.domain

sealed class Container<T>

class PendingContainer<T> : Container<T>()

class SuccessContainer<T>(
    val data: T,
) : Container<T>()

class ErrorContainer<T>(
    val exception: Exception,
) : Container<T>()

fun <T> Container<T>?.takeSuccess(): T? {
    return if (this is SuccessContainer) this.data
    else null
}