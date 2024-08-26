package com.example.orderdeliver.domain

import com.example.orderdeliver.presentation.mappers.base.ToViewItemMapper

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

fun <T, P> Container<T>.mapView(toViewItemMapper: ToViewItemMapper<T, P>): Container<P>{
    return when(this){
        is SuccessContainer -> return SuccessContainer(toViewItemMapper.map(this.data))
        is ErrorContainer -> return ErrorContainer(this.exception)
        is PendingContainer -> return PendingContainer()
    }
}