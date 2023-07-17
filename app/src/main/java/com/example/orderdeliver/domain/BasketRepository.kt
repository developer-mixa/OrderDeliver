package com.example.orderdeliver.domain

import com.example.orderdeliver.data.models.BasketModel

interface BasketRepository {

    fun priceForSubject(basketModel: BasketModel): Int

    fun priceForAll(basketModels: List<BasketModel>): Int

}