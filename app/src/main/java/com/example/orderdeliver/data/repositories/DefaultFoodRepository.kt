package com.example.orderdeliver.data.repositories

import android.content.res.Resources.NotFoundException
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.orderdeliver.data.sources.FoodPageLoader
import com.example.orderdeliver.data.sources.FoodPagingSource
import com.example.orderdeliver.data.sources.FoodSource
import com.example.orderdeliver.domain.models.FoodDataModel
import com.example.orderdeliver.domain.repositories.FoodRepository
import com.example.orderdeliver.presentation.menu.models.TypeFoodModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.min

@Singleton
class DefaultFoodRepository @Inject constructor(
    private val foodSource: FoodSource
): FoodRepository {

    override fun getPagedFoods(foodTypeId: String): Flow<PagingData<FoodDataModel>> {
        val loader : FoodPageLoader =  { pageIndex, pageSize ->
            getFoods(pageIndex, pageSize, foodTypeId)
        }

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { FoodPagingSource(loader, PAGE_SIZE) }
        ).flow
    }

    override suspend fun getAllFoodTypes(): List<TypeFoodModel> {
        return foodSource.getTypeFoods()
    }

    override fun setActivatedTypeFoodById(id: String): List<TypeFoodModel>? {
        return foodSource.setActivatedTypeFoodById(id)
    }

    override suspend fun findFoodById(id: String): FoodDataModel {
        return foodSource.getFoods(id).find { it.id == id } ?: throw NotFoundException()
    }

    private suspend fun getFoods(
        pageIndex: Int,
        pageSize: Int,
        foodTypeId: String
    ): List<FoodDataModel> {

        val foods = foodSource.getFoods(foodTypeId)

        val offset = pageIndex * pageSize

        val limit = min(offset + pageSize, foods.size)
        // TODO (PAGING ON THE BACKEND)
        return foods.slice(offset until limit)
    }

    companion object{
        const val PAGE_SIZE = 10
    }

}