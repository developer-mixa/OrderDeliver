package com.example.orderdeliver.data

import com.example.orderdeliver.data.models.BasketModel
import com.example.orderdeliver.data.models.FoodDataModel
import com.example.orderdeliver.data.models.PaymentModel
import com.example.orderdeliver.domain.BasketRepository
import com.example.orderdeliver.domain.Container
import com.example.orderdeliver.domain.SuccessContainer
import com.example.orderdeliver.domain.exceptions.ReachedLimitException
import com.example.orderdeliver.domain.exceptions.ZeroItemException
import com.example.orderdeliver.utils.showLog
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultBasketRepository @Inject constructor() : BasketRepository {

    private val mapCountBasketsById = mutableMapOf<Int, BasketModel>()

    private val listBasketsFlow = MutableSharedFlow<List<BasketModel>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val allCountBaskets = MutableStateFlow(0)

    override fun priceForSubject(basketModel: BasketModel): Int {
        if (basketModel.count == 0) throw ZeroItemException()

        val mainPrice = basketModel.foodDataModel.price.toFloat()
        val discountPercentage = basketModel.foodDataModel.discount

        val sumPrice = (mainPrice / 100) * discountPercentage

        val priceWithDiscountForSubject = mainPrice - sumPrice

        return (priceWithDiscountForSubject * basketModel.count).toInt()
    }

    override fun priceForAll(basketModels: List<BasketModel>): Int {
        var result = 0
        basketModels.forEach {
            if (it.count == 0) throw ZeroItemException()
            result += priceForSubject(it)
        }
        return result
    }


    override suspend fun addBasket(foodDataModel: FoodDataModel) {
        val countById = (mapCountBasketsById[foodDataModel.id]?.count ?: 0)
        if (countById >= foodDataModel.maxCount){
            throw ReachedLimitException()
        }
        mapCountBasketsById[foodDataModel.id] = BasketModel(foodDataModel, countById + 1)
        listBasketsFlow.emit(mapCountBasketsById.values.toList())
        allCountBaskets.emit(allCountBaskets.first() + 1)
    }

    override suspend fun removeBasket(id: Int) {
        mapCountBasketsById.remove(id)
        allCountBaskets.emit(allCountBaskets.first() - 1)
        listBasketsFlow.emit(mapCountBasketsById.values.toList())
    }

    override suspend fun minusOneBasket(id: Int) {
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
    override fun priceForAllWithoutDiscount(basketModels: List<BasketModel>): Int {
        var result = 0
        basketModels.forEach {
            if (it.count == 0) throw ZeroItemException()
            val subjectWithoutDiscount = it.copy(foodDataModel = it.foodDataModel.copy(discount = 0))
            result += priceForSubject(subjectWithoutDiscount)
        }
        return result
    }

    override fun getDiscountCount(basketModels: List<BasketModel>): Int {
        var resultWithDiscount = 0
        var resultWithoutDiscount = 0

        basketModels.forEach {
            if (it.count == 0) throw ZeroItemException()
            resultWithDiscount += priceForSubject(it)
            val subjectWithoutDiscount = it.copy(foodDataModel = it.foodDataModel.copy(discount = 0))
            resultWithoutDiscount += priceForSubject(subjectWithoutDiscount)
        }
        return resultWithoutDiscount - resultWithDiscount
        //return priceForAllWithoutDiscount(basketModels) - priceForAll(basketModels)
    }

    override suspend fun getPayment(): Container<PaymentModel> {

        val baskets = listBasketsFlow.first()

        delay(1000)

        val payment = PaymentModel(
            UUID.randomUUID().toString(),
            "Сочи, Улица Красная Горка, 18/2",
            "*8829",
            allCountBaskets.value,
            priceForAllWithoutDiscount(baskets),
            getDiscountCount(baskets),
            priceForAll(baskets)

        )

        return SuccessContainer(
            payment
        )
    }


}