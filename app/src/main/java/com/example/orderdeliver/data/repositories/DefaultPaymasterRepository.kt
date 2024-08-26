package com.example.orderdeliver.data.repositories

import android.annotation.SuppressLint
import android.content.Context
import com.example.orderdeliver.R
import com.example.orderdeliver.data.base.BaseRetrofitSource
import com.example.orderdeliver.data.models.RetrofitConfig
import com.example.orderdeliver.domain.models.BasketModel
import com.example.orderdeliver.domain.models.PaymentModel
import com.example.orderdeliver.domain.repositories.AddressRepository
import com.example.orderdeliver.domain.Container
import com.example.orderdeliver.domain.repositories.PaymasterRepository
import com.example.orderdeliver.domain.SuccessContainer
import com.example.orderdeliver.domain.api.ProductsApi
import com.example.orderdeliver.domain.exceptions.ZeroItemException
import com.example.orderdeliver.domain.requests.RequestBuy
import com.example.orderdeliver.domain.usecases.GetAllPriceUseCase
import com.example.orderdeliver.domain.usecases.GetPriceForSubjectUseCase
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import retrofit2.Retrofit
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
    private val moshi: Moshi,
    private val retrofit: Retrofit,
    @ApplicationContext private val context: Context
) : PaymasterRepository, BaseRetrofitSource(moshi) {

    private val productsApi = retrofit.create(ProductsApi::class.java)

    override suspend fun getPayment(
        baskets: List<BasketModel>,
        allBasketsCount: Int
    ): Container<PaymentModel> {
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

    override suspend fun pay(baskets: List<BasketModel>): Container<String> =
        wrapRetrofitExceptions {
            productsApi.buy(RequestBuy(baskets.map { it.toRequestProduct() }))
            return@wrapRetrofitExceptions SuccessContainer(context.getString(R.string.payment_was_successful))
        }

    private fun priceForAllWithoutDiscount(basketModels: List<BasketModel>): Float {
        var result = 0f
        basketModels.forEach {
            if (it.count == 0) throw ZeroItemException()
            val subjectWithoutDiscount =
                it.copy(foodDataModel = it.foodDataModel.copy(priceWithDiscount = null))
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
            val subjectWithoutDiscount =
                it.copy(foodDataModel = it.foodDataModel.copy(priceWithDiscount = null))
            resultWithoutDiscount += getPriceForSubjectUseCase(subjectWithoutDiscount)
        }
        return resultWithoutDiscount - resultWithDiscount
    }
}