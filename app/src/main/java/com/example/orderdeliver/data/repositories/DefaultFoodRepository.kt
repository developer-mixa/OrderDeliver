package com.example.orderdeliver.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.orderdeliver.data.sources.FoodPageLoader
import com.example.orderdeliver.data.sources.FoodPagingSource
import com.example.orderdeliver.data.sources.FoodSource
import com.example.orderdeliver.domain.models.FoodDataModel
import com.example.orderdeliver.domain.repositories.FoodRepository
import com.example.orderdeliver.presentation.menu.models.TypeFoodModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

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

    override suspend fun getAllFoodTypes(): List<TypeFoodModel> = withContext(Dispatchers.IO){
        delay(300)
        return@withContext foodSource.getTypeFoods()
    }

    override fun setActivatedTypeFoodById(id: String): List<TypeFoodModel>? {
        return foodSource.setActivatedTypeFoodById(id)
    }
    private suspend fun getFoods(
        pageIndex: Int,
        pageSize: Int,
        foodTypeId: String
    ): List<FoodDataModel>  = withContext(Dispatchers.IO){
        val offset = pageIndex * pageSize
        delay(1000) // test delay
        return@withContext foodSource.getFoods(foodTypeId, offset, pageSize)
    }

    companion object{
        const val PAGE_SIZE = 10
    }

}