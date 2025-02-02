package com.example.orderdeliver.data.repositories

import com.example.orderdeliver.domain.models.BasketModel
import com.example.orderdeliver.domain.models.FoodDataModel
import com.example.orderdeliver.domain.repositories.BasketRepository
import com.example.orderdeliver.domain.exceptions.ReachedLimitException
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultBasketRepository @Inject constructor(): BasketRepository {

    private val mapCountBasketsById = mutableMapOf<String, BasketModel>()

    private val listBasketsFlow = MutableSharedFlow<List<BasketModel>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val allCountBaskets = MutableStateFlow(0)


    override suspend fun addBasket(foodDataModel: FoodDataModel) {
        val foodId = foodDataModel.fullId()
        val countById = (mapCountBasketsById[foodId]?.count ?: 0)
        if (countById >= foodDataModel.maxCount){
            throw ReachedLimitException()
        }
        mapCountBasketsById[foodId] = BasketModel(foodDataModel, countById + 1)
        listBasketsFlow.emit(mapCountBasketsById.values.toList())
        allCountBaskets.emit(allCountBaskets.first() + 1)
    }

    override suspend fun removeBasket(id: String) {
        mapCountBasketsById.remove(id)
        allCountBaskets.emit(allCountBaskets.first() - 1)
        listBasketsFlow.emit(mapCountBasketsById.values.toList())
    }

    override suspend fun minusOneBasket(id: String) {
        val countById = (mapCountBasketsById[id]?.count ?: 0)
        if (mapCountBasketsById[id] == null || countById - 1 <= 0) {
            removeBasket(id)
            return
        }
        mapCountBasketsById[id] =
            BasketModel(mapCountBasketsById[id]!!.foodDataModel, countById - 1)
        listBasketsFlow.emit(mapCountBasketsById.values.toList())
        allCountBaskets.emit(allCountBaskets.first() - 1)
    }

    override fun listenBaskets(): Flow<List<BasketModel>> = listBasketsFlow
    override fun listenAllCount(): Flow<Int> = allCountBaskets

    override suspend fun clear() {
        mapCountBasketsById.clear()
        listBasketsFlow.emit(emptyList())
        allCountBaskets.emit(0)
    }

}