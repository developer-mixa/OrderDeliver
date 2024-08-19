package com.example.orderdeliver.data.base

open class AppException : RuntimeException {
    constructor(message: String) : super(message)
    constructor(cause: Throwable) : super(cause)
}

class ConnectionException(cause: Throwable) : AppException(cause = cause)

open class BackendException(message: String) : AppException(message)

class ParseJsonException(
    cause: Throwable
) : AppException(cause = cause)