package com.example.orderdeliver.presentation.mappers

import android.annotation.SuppressLint
import android.content.Context
import com.example.orderdeliver.R
import com.example.orderdeliver.domain.models.FoodDataModel
import com.example.orderdeliver.presentation.mappers.base.ToViewItemMapper
import com.example.orderdeliver.presentation.models.FoodListItem
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FoodToListItemMapper @Inject constructor(
    @ApplicationContext private val context: Context
) : ToViewItemMapper<FoodDataModel, FoodListItem> {

    @SuppressLint("StringFormatMatches")
    override fun map(item: FoodDataModel): FoodListItem {
        val price = item.priceWithDiscount ?: item.price

        val priceText = context.getString(R.string.from_price, price)
        val priceWithMateText = if (item.priceWithDiscount != null) context.getString(
            R.string.price_with_mate,
            item.price
        ) else null

        return FoodListItem(
            item,
            priceText,
            priceWithMateText
        )
    }


}