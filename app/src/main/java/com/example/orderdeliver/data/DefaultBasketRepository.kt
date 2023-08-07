package com.example.orderdeliver.data

import com.example.orderdeliver.data.models.BasketModel
import com.example.orderdeliver.data.models.FoodDataModel
import com.example.orderdeliver.domain.BasketRepository
import com.example.orderdeliver.domain.exceptions.ZeroItemException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultBasketRepository @Inject constructor(): BasketRepository {

    private val mapCountBasketsById = mutableMapOf<Int, BasketModel>()

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

    override fun addBasket(foodDataModel: FoodDataModel) {
        val countById = (mapCountBasketsById[foodDataModel.id]?.count ?: 0)
        mapCountBasketsById[foodDataModel.id] = BasketModel(foodDataModel, countById + 1)
    }

    override fun removeBasket(id: Int) {
        mapCountBasketsById.remove(id)
    }

    override fun minusOneBasket(id: Int) {
        val countById = (mapCountBasketsById[id]?.count ?: 0)
        if (mapCountBasketsById[id] == null || countById - 1 <= 0){
            removeBasket(id)
            return
        }
        mapCountBasketsById[id] = BasketModel(mapCountBasketsById[id]!!.foodDataModel, countById - 1)
    }

    override fun getBaskets(): List<BasketModel>? {
        if (mapCountBasketsById.isEmpty()) return null
        return mapCountBasketsById.values.toList()
    }


}