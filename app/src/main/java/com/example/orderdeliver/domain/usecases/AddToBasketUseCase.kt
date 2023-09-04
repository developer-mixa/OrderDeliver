package com.example.orderdeliver.domain.usecases

import com.example.orderdeliver.data.DefaultBasketRepository
import com.example.orderdeliver.data.models.BasketModel
import com.example.orderdeliver.data.models.FoodDataModel
import com.example.orderdeliver.domain.BasketRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddToBasketUseCase @Inject constructor(
    private val basketRepository: BasketRepository,
) {

    suspend operator fun invoke(foodDataModel: FoodDataModel){
        val priceWithDiscount = basketRepository.priceForSubject(BasketModel(foodDataModel,1))
        val foodDataModelWithDiscount = foodDataModel.copy(priceWithDiscount = priceWithDiscount)
        basketRepository.addBasket(foodDataModelWithDiscount)
    }

}