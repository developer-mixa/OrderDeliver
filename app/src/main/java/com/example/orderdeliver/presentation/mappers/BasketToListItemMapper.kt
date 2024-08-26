package com.example.orderdeliver.presentation.mappers

import android.content.Context
import com.example.orderdeliver.domain.models.BasketModel
import com.example.orderdeliver.presentation.mappers.base.ToViewItemMapper
import com.example.orderdeliver.presentation.models.BasketListItem
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BasketToListItemMapper @Inject constructor(
    @ApplicationContext private val context: Context
) : ToViewItemMapper<BasketModel, BasketListItem> {

    override fun map(item: BasketModel): BasketListItem {
        val priceWithoutDiscount = item.foodDataModel.price * item.count
        var finalPriceText = "$priceWithoutDiscount $"
        var withoutDiscountText: String? = null

        if (item.foodDataModel.priceWithDiscount != null) {
            withoutDiscountText = finalPriceText
            finalPriceText = "${item.foodDataModel.priceWithDiscount * item.count} $"
        }

        return BasketListItem(
            finalPriceText,
            withoutDiscountText,
            item.foodDataModel,
            item.count.toString()
        )
    }


}