package com.example.orderdeliver.data.sources

import android.content.Context
import com.example.orderdeliver.R
import com.example.orderdeliver.data.base.BaseRetrofitSource
import com.example.orderdeliver.domain.api.FoodApi
import com.example.orderdeliver.domain.models.FoodDataModel
import com.example.orderdeliver.presentation.menu.models.TypeFoodModel
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodSource @Inject constructor(
    private val moshi: Moshi,
    private val retrofit: Retrofit,
    @ApplicationContext private val context: Context
) : BaseRetrofitSource(moshi) {
    private val foodApi = retrofit.create(FoodApi::class.java)

    private var lastId = ALL_ID

    private var typeFoods: MutableList<TypeFoodModel>? = null

    suspend fun getTypeFoods(): List<TypeFoodModel> = wrapRetrofitExceptions {
        if (typeFoods == null)
            typeFoods =
                mutableListOf(TypeFoodModel(ALL_ID, context.getString(R.string.all), true)).plus(
                    foodApi.getCategories().map {
                        TypeFoodModel(
                            it.id,
                            it.name,
                            false
                        )
                    }
                ).toMutableList()
        return@wrapRetrofitExceptions typeFoods!!
    }

    fun setActivatedTypeFoodById(id: String): List<TypeFoodModel>? {
        if (lastId == id) return null
        if (typeFoods == null) return null

        val prevIndex = typeFoods!!.indexOfFirst { it.id == lastId }
        val newIndex = typeFoods!!.indexOfFirst { it.id == id }

        typeFoods!![prevIndex] = typeFoods!![prevIndex].copy(isActivated = false)
        typeFoods!![newIndex] = typeFoods!![newIndex].copy(isActivated = true)
        lastId = id

        return typeFoods
    }

    suspend fun getFoods(foodTypeId: String, offset: Int, limit: Int): List<FoodDataModel> =
        wrapRetrofitExceptions {
            return@wrapRetrofitExceptions foodApi.getFoods(
                offset,
                limit,
                if (foodTypeId != ALL_ID) listOf(foodTypeId) else null
            ).map {
                // TODO MAKE MAPPER
                FoodDataModel(
                    it.id,
                    it.name,
                    it.description,
                    it.weight.toFloat(),
                    it.price,
                    R.drawable.test_pizza_one,
                    if (it.discount != null) it.discount.value else null,
                    10,
                )
            }
        }


    companion object {
        const val ALL_ID = "ALL"
    }

}