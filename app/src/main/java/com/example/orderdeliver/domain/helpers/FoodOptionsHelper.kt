package com.example.orderdeliver.domain.helpers

import com.example.orderdeliver.data.models.FoodOption
import com.example.orderdeliver.data.models.SetPriceFood
import com.example.orderdeliver.domain.exceptions.InCorrectOptionException
import com.example.orderdeliver.domain.exceptions.LimitOptionsException
import com.example.orderdeliver.utils.showLog
import kotlin.reflect.cast

object FoodOptionsHelper {

    private const val MAX_COUNT_OPTIONS = 5


    /**
     * Takes the number of [FoodOption] for a certain type unequal to zero.
     *
     * @param options -> list of foodOptions.
     * @param optionClass -> class which be FoodOption class.
     * @throws [InCorrectOptionException] -> We throw this exception, if [optionClass] is not [FoodOption], [LimitOptionsException]
     * -> we throw this if food option count > [MAX_COUNT_OPTIONS].
     * @return Int? -> count of options if options > 0, else returning null.
     */
    fun <T: FoodOption> countOptionsByType(options: List<FoodOption>, optionClass: Class<T>): Int? {

        var counter = 0
        options.forEach { foodOption ->
            if (optionClass.isInstance(foodOption)) counter++
            if(counter > 5)
                throw LimitOptionsException()
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

    fun toMap(options: List<FoodOption>): Map<String, List<FoodOption>> {
        val map: MutableMap<String, ArrayList<FoodOption>> = mutableMapOf()

        options.forEach {
            val value = map[it.javaClass.simpleName]
            if (value == null) map[it.javaClass.simpleName] = arrayListOf()
            map[it.javaClass.simpleName]!!.add(it)
        }
        return map

    }

    fun isSetPrice(foodOption: FoodOption): Boolean = foodOption is SetPriceFood


}