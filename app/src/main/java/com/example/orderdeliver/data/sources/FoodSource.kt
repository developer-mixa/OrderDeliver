package com.example.orderdeliver.data.sources

import com.example.orderdeliver.R
import com.example.orderdeliver.data.base.BaseRetrofitSource
import com.example.orderdeliver.domain.api.FoodApi
import com.example.orderdeliver.domain.models.FoodDataModel
import com.example.orderdeliver.domain.models.FoodType
import com.example.orderdeliver.presentation.menu.models.TypeFoodModel
import com.squareup.moshi.Moshi
import kotlinx.coroutines.delay
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodSource @Inject constructor(
    private val moshi: Moshi,
    private val retrofit: Retrofit
) : BaseRetrofitSource(moshi) {
    private val foods: MutableList<FoodDataModel> = mutableListOf()

    private val foodApi = retrofit.create(FoodApi::class.java)

    private var lastId = ""

    private val typeFoods = mutableListOf(
        TypeFoodModel("1", FoodType.ALL, "Всё", true),
        TypeFoodModel("2", FoodType.FOOD, "Еда", false),
        TypeFoodModel("3", FoodType.DRINK, "Коктейли", false),
        TypeFoodModel("4", FoodType.COMBO, "Комбо", false),
        TypeFoodModel("5", FoodType.SAUCE, "Соус", false),
        TypeFoodModel("6", FoodType.SAUCE, "Соус", false),
        TypeFoodModel("7", FoodType.SAUCE, "Соус", false),
    )

    fun getTypeFoods(): List<TypeFoodModel> = typeFoods

    fun setActivatedTypeFoodById(id: String): MutableList<TypeFoodModel>? {
        if (lastId == id) return null

/*        typeFoods[lastId - 1] = typeFoods[lastId - 1].copy(isActivated = false)
        typeFoods[id - 1] = typeFoods[id - 1].copy(isActivated = true)
        lastId = id*/

        return typeFoods
    }

    suspend fun getFoods(foodType: FoodType): List<FoodDataModel> {
        delay(1000)
        return foodApi.getFoods().map {
            FoodDataModel(
                it.id,
                it.name,
                it.description,
                it.weight.toFloat(),
                it.price,
                R.drawable.test_pizza_one,
                FoodType.FOOD,
                0,
                0,
                10
            )
        }
    }

    fun reduceFood(id: String, reducingCount: Int){
        val needIndex = foods.indexOfFirst { it.id == id }
        foods[needIndex] = foods[needIndex].copy(maxCount = foods[needIndex].maxCount - reducingCount)
    }

}