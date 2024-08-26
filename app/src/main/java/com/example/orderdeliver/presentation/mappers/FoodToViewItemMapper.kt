package com.example.orderdeliver.presentation.mappers

import com.example.orderdeliver.domain.models.FoodDataModel
import com.example.orderdeliver.presentation.mappers.base.ToViewItemMapper
import com.example.orderdeliver.presentation.models.FoodItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodToViewItemMapper @Inject constructor() : ToViewItemMapper<FoodDataModel, FoodItem> {
    override fun map(item: FoodDataModel): FoodItem {
        return FoodItem(
            item.name,
            item.description,
            item.imageResource,
            item.priceWithDiscount ?: item.price
        )
    }
}