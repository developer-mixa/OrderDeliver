package com.example.orderdeliver.domain.usecases

import com.example.orderdeliver.domain.models.BasketModel
import com.example.orderdeliver.domain.models.FoodDataModel
import com.example.orderdeliver.domain.repositories.BasketRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddToBasketUseCase @Inject constructor(
    private val getPriceForSubjectUseCase: GetPriceForSubjectUseCase,
    private val basketRepository: BasketRepository,
) {

    suspend fun invoke(foodDataModel: FoodDataModel){
        val priceWithDiscount = getPriceForSubjectUseCase(BasketModel(foodDataModel,1))
        val foodDataModelWithDiscount = foodDataModel.copy(priceWithDiscount = priceWithDiscount)
        basketRepository.addBasket(foodDataModelWithDiscount)
    }

}