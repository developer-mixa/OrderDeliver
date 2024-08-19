package com.example.orderdeliver.domain

import com.example.orderdeliver.catch
import com.example.orderdeliver.domain.models.FoodOption
import com.example.orderdeliver.domain.models.PizzaSize
import com.example.orderdeliver.domain.models.PizzaType
import com.example.orderdeliver.domain.exceptions.LimitOptionsException
import org.junit.Assert.assertEquals
import org.junit.Test

class FoodOptionsRepositoryTest {

    @Test
    fun testCountOptionsByType(){
        val options = listOf<FoodOption>(
            PizzaSize("", 0, 0),
            PizzaSize("", 0, 0),
            PizzaSize("", 0, 0),
            PizzaType(""),
            PizzaType(""),
        )

        val pizzaSizesCount = FoodOptionsHelper.countOptionsByType(options, PizzaSize::class.java)

        assertEquals(pizzaSizesCount, 3)
    }

    @Test
    fun testCountDifferentOptions(){
        val options = listOf<FoodOption>(
            PizzaSize("", 0, 0),
            PizzaSize("", 0, 0),
            PizzaSize("", 0, 0),
            PizzaType(""),
            PizzaType(""),
        )

        val differentOptionsCount = FoodOptionsHelper.countDifferentOptions(options)

        assertEquals(differentOptionsCount, 2)
    }

    @Test
    fun testIsSetPriceClass(){
        val pizzaSize = PizzaSize("", 0, 0)
        val pizzaType = PizzaType("")

        val isSetPricePizzaSize = FoodOptionsHelper.isSetPrice(pizzaSize)
        val isSetPricePizzaType = FoodOptionsHelper.isSetPrice(pizzaType)

        assertEquals(isSetPricePizzaSize, true)
        assertEquals(isSetPricePizzaType, false)
    }

    @Test
    fun testLimitOptionsException(){
        val pizzaSize = PizzaSize("", 0, 0)
        val pizzaSizeList = listOf(pizzaSize, pizzaSize, pizzaSize, pizzaSize, pizzaSize, pizzaSize)

        catch<LimitOptionsException> {
            FoodOptionsHelper.countOptionsByType(pizzaSizeList, PizzaSize::class.java)
        }
    }

    @Test
    fun testToMap(){
        val options = listOf<FoodOption>(
            PizzaSize("", 0, 0),
            PizzaSize("", 0, 0),
            PizzaSize("", 0, 0),
            PizzaSize("", 0, 0),
            PizzaType(""),
            PizzaType(""),
            PizzaType(""),
        )

        val mappedOptions = FoodOptionsHelper.toMap(options)

        assertEquals(listOf(4, 3), mappedOptions.values.map { it.size })
    }

}