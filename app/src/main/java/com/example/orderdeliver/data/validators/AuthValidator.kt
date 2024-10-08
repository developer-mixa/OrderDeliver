package com.example.orderdeliver.data.validators

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthValidator @Inject constructor() {

    fun validEmail(email: String) : Boolean{
        return true
    }

    fun validNumber(email: String) : Boolean{
        return true
    }

}