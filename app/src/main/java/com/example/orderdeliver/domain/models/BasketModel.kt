package com.example.orderdeliver.domain.models

import android.os.Parcelable
import com.example.orderdeliver.domain.requests.RequestProduct
import kotlinx.parcelize.Parcelize

@Parcelize
data class BasketModel(
    val foodDataModel: FoodDataModel,
    val count: Int,
): Parcelable{

    fun toRequestProduct() : RequestProduct {
        return RequestProduct(count, foodDataModel.id)
    }

}
