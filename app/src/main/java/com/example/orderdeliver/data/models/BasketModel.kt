package com.example.orderdeliver.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BasketModel(
    val foodDataModel: FoodDataModel,
    val count: Int,
): Parcelable
