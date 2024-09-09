package com.example.orderdeliver.data.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.orderdeliver.domain.models.FoodDataModel
typealias FoodPageLoader = suspend (pageIndex: Int, pageSize: Int) -> List<FoodDataModel>

class FoodPagingSource(
    private val foodPageLoader: FoodPageLoader,
    private val pageSize: Int
) : PagingSource<Int, FoodDataModel>(){

    override fun getRefreshKey(state: PagingState<Int, FoodDataModel>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FoodDataModel> {
        val pageIndex = params.key ?: 0
        return try {
            val foods = foodPageLoader(pageIndex, params.loadSize)
            return LoadResult.Page(
                data = foods,
                prevKey = if (pageIndex == 0) null else pageIndex - 1,
                nextKey = if (foods.size == params.loadSize) pageIndex + (params.loadSize / pageSize) else null
            )
        } catch (e: Exception){
            LoadResult.Error(throwable = e)
        }

    }

}