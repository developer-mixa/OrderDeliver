package com.example.orderdeliver.presentation.models

import android.os.Parcelable
import com.example.orderdeliver.domain.models.FoodDataModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class BasketListItem(
    val finalPriceText: String,
    val withoutDiscountText: String?,
    val food: FoodDataModel,
    val countText: String
) : Parcelable{

    val imageResource get() = food.imageResource
    val foodName get() = food.name
    val foodId get() = food.fullId()

}