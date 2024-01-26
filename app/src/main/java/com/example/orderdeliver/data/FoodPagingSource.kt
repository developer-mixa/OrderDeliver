package com.example.orderdeliver.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.orderdeliver.data.models.FoodDataModel
import com.example.orderdeliver.utils.showLog

typealias FoodPageLoader = suspend (pageIndex: Int, pageSize: Int) -> List<FoodDataModel>

class FoodPagingSource(
    private val foodPageLoader: FoodPageLoader,
    private val pageSize: Int
) : PagingSource<Int, FoodDataModel>(){

    override fun getRefreshKey(state: PagingState<Int, FoodDataModel>): Int? {
        showLog("take anchor pos: ${state.anchorPosition}", "lol")
        val anchorPosition = state.anchorPosition ?: return null
        showLog("take page: ${state.closestPageToPosition(anchorPosition)}", "lol")
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        showLog("result: ${page.prevKey?.plus(1) ?: page.nextKey?.minus(1)}", "lol")
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FoodDataModel> {
        val pageIndex = params.key ?: 0

        return try {
            val foods = foodPageLoader(pageIndex, params.loadSize)
            showLog("method load in FoodPagingSource: ${foods.size} $pageIndex ${params.loadSize}", "lol")
            return LoadResult.Page(
                data = foods,
                prevKey = if (pageIndex == 0) null else pageIndex - 1,
                nextKey = if(foods.size == params.loadSize) pageIndex + (params.loadSize / pageSize) else null
            )
        } catch (e: Exception){
            showLog("exception: ${e.message}", "eeee")
            LoadResult.Error(throwable = e)
        }

    }

}