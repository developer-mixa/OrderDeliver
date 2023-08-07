package com.example.orderdeliver.domain.usecases

import com.example.orderdeliver.data.DefaultBasketRepository
import com.example.orderdeliver.data.models.FoodDataModel
import com.example.orderdeliver.showLog
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddToBasketUseCase @Inject constructor(
    private val defaultBasketRepository: DefaultBasketRepository
) {

    operator fun invoke(foodDataModel: FoodDataModel){
        defaultBasketRepository.addBasket(foodDataModel)
    }

}