package com.example.orderdeliver.data

import com.example.orderdeliver.data.models.BasketModel
import com.example.orderdeliver.domain.BasketRepository
import com.example.orderdeliver.domain.exceptions.ZeroItemException

class DefaultBasketRepository: BasketRepository {
    override fun priceForSubject(basketModel: BasketModel): Int {
        if (basketModel.count == 0) throw ZeroItemException()
        return basketModel.foodDataModel.price * basketModel.count
    }

    override fun priceForAll(basketModels: List<BasketModel>): Int {
        var result = 0
        basketModels.forEach {
            if (it.count == 0) throw ZeroItemException()
            result += priceForSubject(it)
        }
        return result
    }
}