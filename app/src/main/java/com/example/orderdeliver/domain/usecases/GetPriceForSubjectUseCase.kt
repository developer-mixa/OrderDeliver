package com.example.orderdeliver.domain.usecases

import com.example.orderdeliver.domain.models.BasketModel
import com.example.orderdeliver.domain.exceptions.ZeroItemException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPriceForSubjectUseCase @Inject constructor() {
    operator fun invoke(basketModel: BasketModel): Int{
        if (basketModel.count == 0) throw ZeroItemException()

        val mainPrice = basketModel.foodDataModel.price.toFloat()
        val discountPercentage = basketModel.foodDataModel.discount

        val sumPrice = (mainPrice / 100) * discountPercentage

        val priceWithDiscountForSubject = mainPrice - sumPrice

        return (priceWithDiscountForSubject * basketModel.count).toInt()
    }
}