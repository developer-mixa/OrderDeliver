package com.example.orderdeliver.data.models

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

enum class FoodType { DRINK, FOOD, COMBO, SAUCE, ALL }

@Parcelize
data class FoodDataModel(
    val id: Int,
    val name: String,
    val description: String,
    val weight: Float,
    val price: Int,
    @DrawableRes val imageResource: Int,
    val foodType: FoodType,
    val discount: Int = 0,
    val priceWithDiscount: Int = 0,
    val maxCount: Int = 10000
): Parcelable


