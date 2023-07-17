package com.example.orderdeliver.data

import com.example.orderdeliver.R
import com.example.orderdeliver.data.models.FoodDataModel
import com.example.orderdeliver.data.models.FoodType
import com.example.orderdeliver.presentation.menu.models.TypeFoodModel

class FoodSource {
    private val foods = mutableListOf<FoodDataModel>(
        FoodDataModel(
            1,
            "Пепперони",
            "Пепперони получила свое название в честь основного ингредиента – особого сорта острой колбасы салями. Сама колбаса названа от «Рере» – перец.",
            45f,
            579,
            R.drawable.test_pizza_one,
            FoodType.FOOD
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
            FoodType.FOOD
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
        )


    )

    private val typeFoods = mutableListOf<TypeFoodModel>(
        TypeFoodModel(1,FoodType.ALL, true),
        TypeFoodModel(2,FoodType.FOOD, false),
        TypeFoodModel(3,FoodType.DRINK, false),
        TypeFoodModel(4,FoodType.COMBO, false),
        TypeFoodModel(5,FoodType.SAUCE, false),
        TypeFoodModel(5,FoodType.SAUCE, false),
        TypeFoodModel(5,FoodType.SAUCE, false),
    )

    fun getFoods(foodType: FoodType): List<FoodDataModel> {
        if (foodType == FoodType.ALL) return foods
        return foods.filter { it.foodType == foodType }
    }

    fun getTypes(): List<TypeFoodModel> = typeFoods
}