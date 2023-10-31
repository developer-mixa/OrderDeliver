package com.example.orderdeliver.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class FoodOption: Parcelable

interface SetPriceFood {
    val newPrice: Int
}

@Parcelize
data class PizzaType(
    val type: String
): FoodOption()

@Parcelize
data class PizzaSize(
    val nameSize: String,
    val sizeSm: Int,
    override val newPrice: Int
): FoodOption(), SetPriceFood

/*
* Каждая еда может иметь дополнительные настройки
* Список, различие по классам (получить количество настроек с одинаковым id)
*
*
* */