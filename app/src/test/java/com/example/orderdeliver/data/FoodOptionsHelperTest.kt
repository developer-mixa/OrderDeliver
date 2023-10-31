package com.example.orderdeliver.data

import com.example.orderdeliver.catch
import com.example.orderdeliver.domain.helpers.FoodOptionsHelper
import com.example.orderdeliver.data.models.FoodOption
import com.example.orderdeliver.data.models.PizzaSize
import com.example.orderdeliver.data.models.PizzaType
import com.example.orderdeliver.domain.exceptions.InCorrectOptionException
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
    fun testOptionsByTypeWithException(){
        val options = listOf<FoodOption>(
            PizzaSize("", 0, 0),
            PizzaSize("", 0, 0),
            PizzaSize("", 0, 0),
            PizzaType(""),
            PizzaType(""),
        )

        catch<InCorrectOptionException> {
            FoodOptionsHelper.countOptionsByType(options, String::class.java)
        }

        //Well..
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
        println(listOf(10,12,20))
    }

}