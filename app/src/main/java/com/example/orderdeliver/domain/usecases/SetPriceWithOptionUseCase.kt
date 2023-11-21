package com.example.orderdeliver.domain.usecases

import com.example.orderdeliver.data.models.FoodDataModel
import com.example.orderdeliver.data.models.FoodOption
import com.example.orderdeliver.data.models.SetPriceFood
import com.example.orderdeliver.domain.BasketRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetPriceWithOptionUseCase @Inject constructor(
    private val basketRepository: BasketRepository
) {
    operator fun invoke(basketModel: FoodDataModel, foodOption: FoodOption): FoodDataModel?{
        return if (foodOption is SetPriceFood){
            basketRepository.setPriceFoodById(basketModel, (foodOption as SetPriceFood).newPrice)
        }else null
    }

}