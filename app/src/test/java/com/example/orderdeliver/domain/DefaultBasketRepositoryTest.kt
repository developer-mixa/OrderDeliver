package com.example.orderdeliver.domain

import com.example.orderdeliver.catch
import com.example.orderdeliver.data.DefaultBasketRepository
import com.example.orderdeliver.data.models.BasketModel
import com.example.orderdeliver.data.models.FoodDataModel
import com.example.orderdeliver.data.models.FoodType
import com.example.orderdeliver.domain.exceptions.ZeroItemException
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.lang.AssertionError
import java.lang.IllegalStateException

class DefaultBasketRepositoryTest {

    private val defaultBasketRepository = DefaultBasketRepository()

    @Test
    fun allPriceForSubject() {
        val foodDataModel = FoodDataModel(12, "Пицца", "", 0f, 100, 0, FoodType.FOOD)
        val basketModel = BasketModel(foodDataModel, 5)
        val result = defaultBasketRepository.priceForSubject(basketModel)

        assertEquals(500, result)
    }

    @Test
    fun priceForAll() {
        val foodDataModel1 = FoodDataModel(12, "Пицца", "", 0f, 100, 0, FoodType.FOOD)
        val basketModel1 = BasketModel(foodDataModel1, 5)
        val foodDataModel2 = FoodDataModel(13, "Пицца", "", 0f, 50, 0, FoodType.FOOD)
        val basketModel2 = BasketModel(foodDataModel2, 1)
        val foodDataModel3 = FoodDataModel(14, "Пицца", "", 0f, 10, 0, FoodType.FOOD)
        val basketModel3 = BasketModel(foodDataModel3, 2)
        val list = listOf(basketModel1, basketModel2, basketModel3)

        val result = defaultBasketRepository.priceForAll(list)

        assertEquals(570, result)
    }

    @Test
    fun throwExceptionIfSubjectCountIsZero() {
        val foodDataModel = FoodDataModel(12, "Пицца", "", 0f, 100, 0, FoodType.FOOD)
        val basketModel = BasketModel( foodDataModel, 0)

        catch<ZeroItemException> { defaultBasketRepository.priceForSubject(basketModel) }
    }

    @Test
    fun throwExceptionIfOneFromSubjectCountIsZero() {
        val foodDataModel1 = FoodDataModel(12, "Пицца", "", 0f, 100, 0, FoodType.FOOD)
        val basketModel1 = BasketModel( foodDataModel1, 5)
        val foodDataModel2 = FoodDataModel(13, "Пицца", "", 0f, 50, 0, FoodType.FOOD)
        val basketModel2 = BasketModel( foodDataModel2, 0)
        val foodDataModel3 = FoodDataModel(14, "Пицца", "", 0f, 10, 0, FoodType.FOOD)
        val basketModel3 = BasketModel( foodDataModel3, 2)
        val list = listOf(basketModel1, basketModel2, basketModel3)

        catch<ZeroItemException> { defaultBasketRepository.priceForAll(list) }
    }

    @Test
    fun addToBasketFreeSameItems(){
        val food = FoodDataModel(1, "Пицца", "", 0f, 100, 0, FoodType.FOOD)

        defaultBasketRepository.addBasket(food)
        defaultBasketRepository.addBasket(food)
        defaultBasketRepository.addBasket(food)

        assertEquals(BasketModel(food,3), defaultBasketRepository.getBaskets()!![0] )
    }

    @Test
    fun addToBasketTwoDifferentItem(){
        val food1 = FoodDataModel(1, "Пицца", "", 0f, 100, 0, FoodType.FOOD)
        val food2 = FoodDataModel(2, "Пирожок", "", 0f, 100, 0, FoodType.FOOD)

        defaultBasketRepository.addBasket(food1)
        defaultBasketRepository.addBasket(food1)
        defaultBasketRepository.addBasket(food2)

        assertEquals(BasketModel(food1,2), defaultBasketRepository.getBaskets()!![0] )
        assertEquals(BasketModel(food2,1), defaultBasketRepository.getBaskets()!![1] )
    }
    @Test
    fun checkReturnNullIfZeroItems(){
        assertNull(defaultBasketRepository.getBaskets())
    }

    @Test
    fun checkDeleteBasket(){
        val food1 = FoodDataModel(1, "Пицца", "", 0f, 100, 0, FoodType.FOOD)
        val food2 = FoodDataModel(2, "Пирожок", "", 0f, 100, 0, FoodType.FOOD)

        defaultBasketRepository.addBasket(food1)
        defaultBasketRepository.addBasket(food2)
        defaultBasketRepository.removeBasket(1)
        defaultBasketRepository.removeBasket(2)

        assertNull(defaultBasketRepository.getBaskets())
    }

    @Test
    fun checkMinusBasketIfCountEqualsZeroRemovesProduct(){
        val food1 = FoodDataModel(1, "Пицца", "", 0f, 100, 0, FoodType.FOOD)
        defaultBasketRepository.addBasket(food1)

        defaultBasketRepository.minusOneBasket(1)

        assertNull(defaultBasketRepository.getBaskets())
    }

    @Test
    fun checkMinusBasket(){
        val food1 = FoodDataModel(1, "Пицца", "", 0f, 100, 0, FoodType.FOOD)
        val food2 = FoodDataModel(2, "Пицца", "", 0f, 100, 0, FoodType.FOOD)
        defaultBasketRepository.addBasket(food1)
        defaultBasketRepository.addBasket(food1)
        defaultBasketRepository.addBasket(food2)
        defaultBasketRepository.addBasket(food2)

        defaultBasketRepository.minusOneBasket(1)

        assertEquals(2,defaultBasketRepository.getBaskets()!!.size)
    }

    @Test
    fun checkDoubleMinusBasketRemovesBasket(){
        val food1 = FoodDataModel(1, "Пицца", "", 0f, 100, 0, FoodType.FOOD)
        val food2 = FoodDataModel(2, "Пицца", "", 0f, 100, 0, FoodType.FOOD)
        defaultBasketRepository.addBasket(food1)
        defaultBasketRepository.addBasket(food1)
        defaultBasketRepository.addBasket(food2)
        defaultBasketRepository.addBasket(food2)

        defaultBasketRepository.minusOneBasket(1)
        defaultBasketRepository.minusOneBasket(1)

        assertEquals(1,defaultBasketRepository.getBaskets()!!.size)
    }

}