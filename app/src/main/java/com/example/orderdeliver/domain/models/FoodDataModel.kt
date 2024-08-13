package com.example.orderdeliver.domain.models

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.example.orderdeliver.domain.exceptions.InCorrectOptionException
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodDataModel(
    val id: String,
    val name: String,
    val description: String,
    val weight: Float,
    val price: Float,
    @DrawableRes val imageResource: Int,
    val priceWithDiscount: Float? = null,
    val maxCount: Int = 10,
    val options: List<FoodOption>? = null
): Parcelable{

    fun fullId(): String{
        return "$id$name$price$priceWithDiscount"
    }

}


