package com.example.orderdeliver.presentation.mappers

import android.annotation.SuppressLint
import android.content.Context
import com.example.orderdeliver.R
import com.example.orderdeliver.domain.models.FoodDataModel
import com.example.orderdeliver.presentation.models.FoodListItem
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FoodToListItemMapper @Inject constructor(
    @ApplicationContext private val context: Context
) {

    @SuppressLint("StringFormatMatches")
    fun map(food: FoodDataModel): FoodListItem {
        val price = food.priceWithDiscount ?: food.price

        val priceText = context.getString(R.string.from_price, price)
        val priceWithMateText = if (food.priceWithDiscount != null) context.getString(
            R.string.price_with_mate,
            food.price
        ) else null

        return FoodListItem(
            food,
            priceText,
            priceWithMateText
        )
    }

}