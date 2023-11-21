package com.example.orderdeliver.data.models

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.versionedparcelable.NonParcelField
import com.example.orderdeliver.domain.exceptions.InCorrectOptionException
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
    val maxCount: Int = 10000,
    val options: List<FoodOption>? = null
): Parcelable{

    init {
        var inCorrectPrice = true
        var hasPriceFood = false
        if (options != null){
            for (i in 0 until options.size){
                val foodOption = options[i]

                if (foodOption !is SetPriceFood) continue

                hasPriceFood = true

                if (foodOption.newPrice == price)
                    inCorrectPrice = false
            }
        }else{
            inCorrectPrice = false
        }
        if (inCorrectPrice && hasPriceFood) throw InCorrectOptionException("Current price=$price must be a one of foodOption prices!")
    }

    fun fullId(): String{
        return "$id$name$price$priceWithDiscount"
    }
}


