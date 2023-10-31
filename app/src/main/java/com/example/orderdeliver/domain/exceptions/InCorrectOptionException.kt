package com.example.orderdeliver.domain.exceptions

import java.lang.Exception

class InCorrectOptionException(message: String): TypeCastException(message)

class LimitOptionsException : Exception("You can not add options more than five!")