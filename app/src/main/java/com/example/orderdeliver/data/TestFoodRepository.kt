package com.example.orderdeliver.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.orderdeliver.data.models.FoodDataModel
import com.example.orderdeliver.data.models.FoodType
import com.example.orderdeliver.domain.FoodRepository
import com.example.orderdeliver.presentation.menu.models.TypeFoodModel
import com.example.orderdeliver.utils.showLog
import kotlinx.coroutines.flow.Flow
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.max
import kotlin.math.min

@Singleton
class TestFoodRepository @Inject constructor(
    private val foodSource: FoodSource
): FoodRepository {
    override fun getPagedFoods(foodType: FoodType): Flow<PagingData<FoodDataModel>> {
        val loader : FoodPageLoader =  {pageIndex, pageSize ->
            getFoods(pageIndex, pageSize, foodType)
        }
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { FoodPagingSource(loader, PAGE_SIZE) }
        ).flow
    }

    override fun getAllFoodTypes(): List<TypeFoodModel> {
        return foodSource.getTypeFoods()
    }

    override fun setActivatedTypeFoodById(id: Int): MutableList<TypeFoodModel>? {
        return foodSource.setActivatedTypeFoodById(id)
    }

    private suspend fun getFoods(
        pageIndex: Int,
        pageSize: Int,
        foodType: FoodType
    ): List<FoodDataModel> {
        val foods = foodSource.getFoods(foodType)

        val offset = pageIndex * pageSize

        val limit = min(offset + pageSize, foods.size)

        return foods.slice(offset until limit)
    }

    companion object{
        const val PAGE_SIZE = 10
    }

}