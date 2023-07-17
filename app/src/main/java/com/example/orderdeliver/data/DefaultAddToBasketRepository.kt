package com.example.orderdeliver.data

import com.example.orderdeliver.data.models.FoodDataModel
import com.example.orderdeliver.domain.AddToBasketRepository

class DefaultAddToBasketRepository: AddToBasketRepository {
    override fun getPrice(price: Int,discount: Int?): Int {
        if (discount == null)return price

        var result = price.toFloat()
        result = result / 100 * discount
        return result.toInt()
    }

    override fun add(foodDataModel: FoodDataModel): FoodDataModel {
        TODO("Not yet implemented")
    }
}