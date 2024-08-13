package com.example.orderdeliver.data.repositories

import android.annotation.SuppressLint
import com.example.orderdeliver.domain.models.BasketModel
import com.example.orderdeliver.domain.models.PaymentModel
import com.example.orderdeliver.domain.repositories.AddressRepository
import com.example.orderdeliver.domain.Container
import com.example.orderdeliver.domain.repositories.PaymasterRepository
import com.example.orderdeliver.domain.SuccessContainer
import com.example.orderdeliver.domain.exceptions.ZeroItemException
import com.example.orderdeliver.domain.sources.OrderStorySource
import com.example.orderdeliver.domain.usecases.GetAllPriceUseCase
import com.example.orderdeliver.domain.usecases.GetPriceForSubjectUseCase
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultPaymasterRepository @Inject constructor(
    private val getAllPriceUseCase: GetAllPriceUseCase,
    private val getPriceForSubjectUseCase: GetPriceForSubjectUseCase,
    private val addressRepository: AddressRepository,
    private val orderStorySource: OrderStorySource
): PaymasterRepository {
    override suspend fun getPayment(baskets: List<BasketModel>, allBasketsCount: Int): Container<PaymentModel> {
        delay(1000)
        val payment = PaymentModel(
            UUID.randomUUID().toString(),
            addressRepository.getAddress(),
            "*8829",
            allBasketsCount,
            priceForAllWithoutDiscount(baskets),
            getDiscountCount(baskets),
            getAllPriceUseCase(baskets),
            baskets,
            currentDate()
        )

        return SuccessContainer(
            payment
        )
    }

    @SuppressLint("SimpleDateFormat")
    private fun currentDate(): String {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        return sdf.format(Date())
    }

    override suspend fun pay(payment: PaymentModel): Container<String> {
        //paying...
        orderStorySource.add(payment)
        return SuccessContainer("Платёж прошёл успешно!")
    }

    private fun priceForAllWithoutDiscount(basketModels: List<BasketModel>): Float {
        var result = 0f
        basketModels.forEach {
            if (it.count == 0) throw ZeroItemException()
            val subjectWithoutDiscount = it.copy(foodDataModel = it.foodDataModel.copy(priceWithDiscount = null))
            result += getPriceForSubjectUseCase(subjectWithoutDiscount)
        }
        return result
    }
    private fun getDiscountCount(basketModels: List<BasketModel>): Float {
        var resultWithDiscount = 0f
        var resultWithoutDiscount = 0f

        basketModels.forEach {
            if (it.count == 0) throw ZeroItemException()
            resultWithDiscount += getPriceForSubjectUseCase(it)
            val subjectWithoutDiscount = it.copy(foodDataModel = it.foodDataModel.copy(priceWithDiscount = null))
            resultWithoutDiscount += getPriceForSubjectUseCase(subjectWithoutDiscount)
        }
        return resultWithoutDiscount - resultWithDiscount
    }
}