package com.example.orderdeliver.domain

import com.example.orderdeliver.catch
import com.example.orderdeliver.data.repositories.DefaultBasketRepository
import com.example.orderdeliver.domain.models.BasketModel
import com.example.orderdeliver.domain.models.FoodDataModel
import com.example.orderdeliver.domain.models.FoodType
import com.example.orderdeliver.domain.exceptions.ReachedLimitException
import com.example.orderdeliver.domain.exceptions.ZeroItemException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
@OptIn(ExperimentalCoroutinesApi::class)
class DefaultBasketRepositoryTest {

    private val defaultBasketRepository = DefaultBasketRepository()

    @Test
    fun allPriceForSubjectWithoutDiscount() {
        val foodDataModel = FoodDataModel(12, "Пицца", "", 0f, 10, 0, FoodType.FOOD)
        val basketModel = BasketModel(foodDataModel, 2)
        val result = defaultBasketRepository.priceForSubject(basketModel)

        assertEquals(20, result)
    }

    @Test
    fun getDiscountCountTest() {
        val foodDataModel1 = FoodDataModel(12, "Пицца", "", 0f, 100, 0, FoodType.FOOD, 50)
        val basketModel1 = BasketModel(foodDataModel1, 5)
        //   print(defaultBasketRepository.priceForSubject(basketModel1))
        val foodDataModel2 = FoodDataModel(13, "Пицца", "", 0f, 50, 0, FoodType.FOOD, 50)
        val basketModel2 = BasketModel(foodDataModel2, 2)
        print(defaultBasketRepository.priceForSubject(basketModel2))
        val foodDataModel3 = FoodDataModel(14, "Пицца", "", 0f, 10, 0, FoodType.FOOD)
        val basketModel3 = BasketModel(foodDataModel3, 2)
        //    print(defaultBasketRepository.priceForSubject(basketModel3))
        val list = listOf(basketModel1, basketModel2, basketModel3)

        val discountSum = defaultBasketRepository.getDiscountCount(list)
        //250 + 25 + 20 = 295 || 500 + 50 + 20 = 570

        assertEquals(300, discountSum)
    }


    @Test
    fun priceForAllWithoutDiscount() {
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
    fun allPriceForSubjectWithDiscount() {
        val foodDataModel = FoodDataModel(12, "Пицца", "", 0f, 50, 0, FoodType.FOOD, 50)
        val basketModel = BasketModel(foodDataModel, 1)
        val result = defaultBasketRepository.priceForSubject(basketModel)

        assertEquals(25, result)
    }

    @Test
    fun allPriceForSubjectWithDiscountWithSomeCount() {
        val foodDataModel = FoodDataModel(12, "Пицца", "", 0f, 100, 0, FoodType.FOOD, 50)
        val basketModel = BasketModel(foodDataModel, 5)
        val result = defaultBasketRepository.priceForSubject(basketModel)

        assertEquals(250, result)
    }

    @Test
    fun priceForAllWithDiscount() {
        val foodDataModel1 = FoodDataModel(12, "Пицца", "", 0f, 100, 0, FoodType.FOOD, 50)
        val basketModel1 = BasketModel(foodDataModel1, 5) //250
        val foodDataModel2 = FoodDataModel(13, "Пицца", "", 0f, 50, 0, FoodType.FOOD)
        val basketModel2 = BasketModel(foodDataModel2, 1) //50
        val foodDataModel3 = FoodDataModel(14, "Пицца", "", 0f, 10, 0, FoodType.FOOD)
        val basketModel3 = BasketModel(foodDataModel3, 2) //20
        val list = listOf(basketModel1, basketModel2, basketModel3)

        val result = defaultBasketRepository.priceForAll(list)

        assertEquals(320, result)
    }

    @Test
    fun throwExceptionIfSubjectCountIsZero() {
        val foodDataModel = FoodDataModel(12, "Пицца", "", 0f, 100, 0, FoodType.FOOD)
        val basketModel = BasketModel(foodDataModel, 0)

        catch<ZeroItemException> { defaultBasketRepository.priceForSubject(basketModel) }
    }

    @Test
    fun throwExceptionIfOneFromSubjectCountIsZero() {
        val foodDataModel1 = FoodDataModel(12, "Пицца", "", 0f, 100, 0, FoodType.FOOD)
        val basketModel1 = BasketModel(foodDataModel1, 5)
        val foodDataModel2 = FoodDataModel(13, "Пицца", "", 0f, 50, 0, FoodType.FOOD)
        val basketModel2 = BasketModel(foodDataModel2, 0)
        val foodDataModel3 = FoodDataModel(14, "Пицца", "", 0f, 10, 0, FoodType.FOOD)
        val basketModel3 = BasketModel(foodDataModel3, 2)
        val list = listOf(basketModel1, basketModel2, basketModel3)

        catch<ZeroItemException> { defaultBasketRepository.priceForAll(list) }
    }

    @Test
    fun addToBasketFreeSameItems() = runTest {
        val food = FoodDataModel(1, "Пицца", "", 0f, 100, 0, FoodType.FOOD)

        defaultBasketRepository.addBasket(food)
        defaultBasketRepository.addBasket(food)
        defaultBasketRepository.addBasket(food)

        val list = defaultBasketRepository.listenBaskets().first()
        assertEquals(BasketModel(food, 3), list[0])
    }

    @Test
    fun addToBasketTwoDifferentItem() = runTest {
        val food1 = FoodDataModel(1, "Пицца", "", 0f, 100, 0, FoodType.FOOD)
        val food2 = FoodDataModel(2, "Пирожок", "", 0f, 100, 0, FoodType.FOOD)

        defaultBasketRepository.addBasket(food1)
        defaultBasketRepository.addBasket(food1)
        defaultBasketRepository.addBasket(food2)


        val list = defaultBasketRepository.listenBaskets().first()

        assertEquals(BasketModel(food1, 2), list[0])
        assertEquals(BasketModel(food2, 1), list[1])
    }

    @Test
    fun whenLimitReachedInAddBasketException() = runTest{
        val food = FoodDataModel(1, "Пицца", "", 0f, 100, 0, FoodType.FOOD, maxCount = 3)

        defaultBasketRepository.addBasket(food)
        defaultBasketRepository.addBasket(food)
        defaultBasketRepository.addBasket(food)

        catch<ReachedLimitException> { defaultBasketRepository.addBasket(food) }
    }

    @Test
    fun checkDeleteBasket() = runTest {
        val food1 = FoodDataModel(1, "Пицца", "", 0f, 100, 0, FoodType.FOOD)
        val food2 = FoodDataModel(2, "Пирожок", "", 0f, 100, 0, FoodType.FOOD)

        defaultBasketRepository.addBasket(food1)
        defaultBasketRepository.addBasket(food2)
        defaultBasketRepository.removeBasket(1)
        defaultBasketRepository.removeBasket(2)

        val list = defaultBasketRepository.listenBaskets().first()

        assertEquals(0, list.size)
    }

    @Test
    fun checkMinusBasketIfCountEqualsZeroRemovesProduct() = runTest {
        val food1 = FoodDataModel(1, "Пицца", "", 0f, 100, 0, FoodType.FOOD)

        defaultBasketRepository.addBasket(food1)
        defaultBasketRepository.minusOneBasket(1)

        val list = defaultBasketRepository.listenBaskets().first()
        assertEquals(0, list.size)
    }

    @Test
    fun checkMinusBasket() = runTest {
        val food1 = FoodDataModel(1, "Пицца", "", 0f, 100, 0, FoodType.FOOD)
        val food2 = FoodDataModel(2, "Пицца", "", 0f, 100, 0, FoodType.FOOD)
        defaultBasketRepository.addBasket(food1)
        defaultBasketRepository.addBasket(food1)
        defaultBasketRepository.addBasket(food2)
        defaultBasketRepository.addBasket(food2)

        defaultBasketRepository.minusOneBasket(1)

        val list = defaultBasketRepository.listenBaskets().first()
        assertEquals(2, list.size)
    }

    @Test
    fun checkDoubleMinusBasketRemovesBasket() = runTest {
        val food1 = FoodDataModel(1, "Пицца", "", 0f, 100, 0, FoodType.FOOD)
        val food2 = FoodDataModel(2, "Пицца", "", 0f, 100, 0, FoodType.FOOD)
        defaultBasketRepository.addBasket(food1)
        defaultBasketRepository.addBasket(food1)
        defaultBasketRepository.addBasket(food2)
        defaultBasketRepository.addBasket(food2)

        defaultBasketRepository.minusOneBasket(1)
        defaultBasketRepository.minusOneBasket(1)

        val list = defaultBasketRepository.listenBaskets().first()
        assertEquals(1, list.size)
    }

}