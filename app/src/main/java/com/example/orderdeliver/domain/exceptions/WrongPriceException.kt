package com.example.orderdeliver.domain.exceptions

class WrongPriceException(value: Float) : Exception("The price must be more than 0, but your price is $value")