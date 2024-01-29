package com.example.orderdeliver.data

import com.example.orderdeliver.data.sources.FoodSource
import org.junit.Assert
import org.junit.Test

class FoodSourceTest {

    private val foodSource = FoodSource()

    @Test
    fun testSetActivatedFoodById(){


        foodSource.setActivatedTypeFoodById(3)

        Assert.assertEquals(true, foodSource.getTypeFoods()[2].isActivated)
        Assert.assertEquals(false, foodSource.getTypeFoods()[0].isActivated)

        foodSource.setActivatedTypeFoodById(6)

        Assert.assertEquals(true, foodSource.getTypeFoods()[5].isActivated)
        Assert.assertEquals(false, foodSource.getTypeFoods()[2].isActivated)
    }

}