package com.example.orderdeliver.data.sources

import com.example.orderdeliver.R
import com.example.orderdeliver.domain.models.FoodDataModel
import com.example.orderdeliver.domain.models.FoodType
import com.example.orderdeliver.domain.models.PizzaSize
import com.example.orderdeliver.domain.models.PizzaType
import com.example.orderdeliver.presentation.menu.models.TypeFoodModel
import com.example.orderdeliver.utils.showLog
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodSource @Inject constructor() {
    private val differentFoods = mutableListOf(
        FoodDataModel(
            1,
            "Пепперони",
            "Пепперони получила свое название в честь основного ингредиента – особого сорта острой колбасы салями. Сама колбаса названа от «Рере» – перец.",
            45f,
            579,
            R.drawable.test_pizza_one,
            FoodType.FOOD,
            maxCount = 5,
            options = listOf(
                PizzaType("Традиционная"),
                PizzaType("Кавказкая"),
                PizzaSize("Маленькая", 25, 489, false),
                PizzaSize("Средняя", 30, 579, true),
                PizzaSize("Большая", 35, 679, false)
            )
        ),
        FoodDataModel(
            2,
            "Пицца от шефа",
            "В составе пиццы на упаковке: моцарелла, ананасы, креветки. Все ингредиенты доступные, а вкус замечательный.",
            45f,
            660,
            R.drawable.test_pizza_two,
            FoodType.FOOD
        ),
        FoodDataModel(
            3,
            "Пепперони фрэш",
            "Пикантная пепперони, увеличенная порция моцареллы, томаты, фирменный томатный соус. В отличие от обычной пепперони, содержит помидоры.",
            45f,
            869,
            R.drawable.test_pizza_thrid,
            FoodType.FOOD
        ),
        FoodDataModel(
            4,
            "Пепперони",
            "Пепперони получила свое название в честь основного ингредиента – особого сорта острой колбасы салями. Сама колбаса названа от «Рере» – перец.",
            45f,
            579,
            R.drawable.test_pizza_one,
            FoodType.FOOD,
            50
        ),
        FoodDataModel(
            5,
            "Пицца от шефа",
            "В составе пиццы на упаковке: моцарелла, ананасы, креветки. Все ингредиенты доступные, а вкус замечательный.",
            45f,
            660,
            R.drawable.test_pizza_two,
            FoodType.FOOD
        ),
        FoodDataModel(
            6,
            "Пепперони фрэш",
            "Пикантная пепперони, увеличенная порция моцареллы, томаты, фирменный томатный соус. В отличие от обычной пепперони, содержит помидоры.",
            45f,
            869,
            R.drawable.test_pizza_thrid,
            FoodType.FOOD
        ),
        FoodDataModel(
            7,
            "Клубничный коктейл",
            "напиток, получаемый смешиванием нескольких компонентов. Чаще всего коктейли представляют собой омбинацию спиртных напитков.",
            12f,
            99,
            R.drawable.test_cocteil,
            FoodType.DRINK,
            50
        ),
        FoodDataModel(
            8,
            "Банановый коктейл",
            "напиток, получаемый смешиванием нескольких компонентов. Чаще всего коктейли представляют собой омбинацию спиртных напитков.",
            12f,
            199,
            R.drawable.test_cocteil_2,
            FoodType.DRINK
        ),


    )
    private val foods: MutableList<FoodDataModel> = mutableListOf()

    init {
        for (i in 1 until 81){
            val randomFood = differentFoods.random()
            val food = randomFood.copy(id = i, name = "${randomFood.name}: $i")

            foods.add(food)
        }
    }

    private var lastId = 1

    private val typeFoods = mutableListOf<TypeFoodModel>(
        TypeFoodModel(1, FoodType.ALL, "Всё", true),
        TypeFoodModel(2, FoodType.FOOD, "Еда", false),
        TypeFoodModel(3, FoodType.DRINK, "Коктейли", false),
        TypeFoodModel(4, FoodType.COMBO, "Комбо", false),
        TypeFoodModel(5, FoodType.SAUCE, "Соус", false),
        TypeFoodModel(6, FoodType.SAUCE, "Соус", false),
        TypeFoodModel(7, FoodType.SAUCE, "Соус", false),
    )

    fun getTypeFoods(): List<TypeFoodModel> = typeFoods

    fun setActivatedTypeFoodById(id: Int): MutableList<TypeFoodModel>? {
        if (lastId == id) return null

        typeFoods[lastId - 1] = typeFoods[lastId - 1].copy(isActivated = false)
        typeFoods[id - 1] = typeFoods[id - 1].copy(isActivated = true)
        lastId = id

        return typeFoods
    }

    suspend fun getFoods(foodType: FoodType): List<FoodDataModel> {
        delay(1000)
        if (foodType == FoodType.ALL) return foods
        return foods.filter { it.foodType == foodType }
    }

    fun reduceFood(id: Int, reducingCount: Int){
        val needIndex = foods.indexOfFirst { it.id == id }
        foods[needIndex] = foods[needIndex].copy(maxCount = foods[needIndex].maxCount - reducingCount)
        showLog("asda da da da  da ad ${needIndex}", "a osdpa msd piaks dpiak sd")
    }

}