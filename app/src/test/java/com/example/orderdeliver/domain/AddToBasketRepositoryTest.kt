package com.example.orderdeliver.domain

import com.example.orderdeliver.data.DefaultAddToBasketRepository
import org.junit.Assert
import org.junit.Test

class AddToBasketRepositoryTest {

    private val defaultRepository = DefaultAddToBasketRepository()

    @Test
    fun testPriceWithDiscount(){
        val price = defaultRepository.getPrice(price = 100, discount = 50)
        Assert.assertEquals(50, price)
    }

    @Test
    fun testPriceWithoutDiscount(){
        val price = defaultRepository.getPrice(price = 100, discount = null)
        Assert.assertEquals(100, price)
    }


}