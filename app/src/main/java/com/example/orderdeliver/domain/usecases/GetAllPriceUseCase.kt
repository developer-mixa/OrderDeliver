package com.example.orderdeliver.domain.usecases

import com.example.orderdeliver.domain.models.BasketModel
import com.example.orderdeliver.domain.exceptions.ZeroItemException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAllPriceUseCase @Inject constructor(
    private val getPriceForSubjectUseCase: GetPriceForSubjectUseCase
) {
    operator fun invoke(basketModels: List<BasketModel>): Float{
        var result = 0f
        basketModels.forEach {
            if (it.count == 0) throw ZeroItemException()
            result += getPriceForSubjectUseCase(it)
        }
        return result
    }
}