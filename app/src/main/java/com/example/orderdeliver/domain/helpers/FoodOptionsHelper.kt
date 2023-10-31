package com.example.orderdeliver.domain.helpers

import com.example.orderdeliver.data.models.FoodOption
import com.example.orderdeliver.data.models.SetPriceFood
import com.example.orderdeliver.domain.exceptions.InCorrectOptionException
import com.example.orderdeliver.domain.exceptions.LimitOptionsException

object FoodOptionsHelper {

    private const val MAX_COUNT_OPTIONS = 5


    /**
     * Takes the number of [FoodOption] for a certain type unequal to zero.
     *
     * @param options -> list of foodOptions.
     * @param optionClass -> class which be FoodOption class.
     * @throws [InCorrectOptionException] -> We throw this exception, if [optionClass] is not [FoodOption].
     * @return Int? -> count of options if options > 0, else returning null.
     */
    fun <T>countOptionsByType(options: List<FoodOption>, optionClass: Class<T>): Int? {

        if (!optionClass.isInstance(optionClass)){
            val errorMessage = "${optionClass.name} must be FoodOption class!"
            throw InCorrectOptionException(errorMessage)
        }

        var counter = 0
        options.forEach { foodOption->
            if (optionClass.isInstance(foodOption)) counter++
        }

        if (counter == 0) return null

        return counter
    }

    /**
     * Takes the number of different [FoodOption] for a certain type unequal to zero.
     *
     * @param options -> list of foodOptions.
     * @throws [LimitOptionsException] if different options > [MAX_COUNT_OPTIONS], we throw this.
     * @return Int? -> count of different options if this > 0, else returning null.
     */
    fun countDifferentOptions(options: List<FoodOption>): Int? {
        val uniqueOptions = mutableSetOf<FoodOption>()
        options.forEach {
            uniqueOptions.add(it)
        }

        val uniqueOptionsSize = uniqueOptions.size

        if (uniqueOptionsSize == 0) return null

        if (uniqueOptionsSize > MAX_COUNT_OPTIONS) throw LimitOptionsException()

        return uniqueOptionsSize
    }

    fun isSetPrice(foodOption: FoodOption): Boolean = foodOption is SetPriceFood


}