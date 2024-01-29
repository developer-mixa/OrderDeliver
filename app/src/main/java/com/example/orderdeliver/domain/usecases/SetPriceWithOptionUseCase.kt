package com.example.orderdeliver.domain.usecases

import com.example.orderdeliver.domain.models.FoodDataModel
import com.example.orderdeliver.domain.models.FoodOption
import com.example.orderdeliver.domain.models.SetPriceFood
import com.example.orderdeliver.domain.repositories.BasketRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetPriceWithOptionUseCase @Inject constructor(
    private val basketRepository: BasketRepository
) {
    operator fun invoke(food: FoodDataModel, foodOption: FoodOption): FoodDataModel?{
        return if (foodOption is SetPriceFood){
            basketRepository.setPriceFoodById(food, (foodOption as SetPriceFood).newPrice)
        }else null
    }

}