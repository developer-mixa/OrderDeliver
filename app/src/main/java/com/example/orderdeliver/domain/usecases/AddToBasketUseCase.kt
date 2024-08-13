package com.example.orderdeliver.domain.usecases

import com.example.orderdeliver.domain.models.BasketModel
import com.example.orderdeliver.domain.models.FoodDataModel
import com.example.orderdeliver.domain.repositories.BasketRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddToBasketUseCase @Inject constructor(
    private val basketRepository: BasketRepository
) {

    suspend fun invoke(foodDataModel: FoodDataModel){
        basketRepository.addBasket(foodDataModel)
    }

}