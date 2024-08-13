package com.example.orderdeliver.domain.usecases

import com.example.orderdeliver.domain.models.BasketModel
import com.example.orderdeliver.domain.exceptions.ZeroItemException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPriceForSubjectUseCase @Inject constructor() {
    operator fun invoke(basketModel: BasketModel): Float{
        if (basketModel.count == 0) throw ZeroItemException()

        val price = basketModel.foodDataModel.priceWithDiscount ?: basketModel.foodDataModel.price

        return price * basketModel.count
    }
}