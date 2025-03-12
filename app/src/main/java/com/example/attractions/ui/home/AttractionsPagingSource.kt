package com.example.attractions.ui.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.attractions.model.attraction.RespAttractionsAll
import com.example.attractions.repository.IRepository
import com.example.attractions.repository.NetworkResponse

class AttractionsPagingSource(
    private val repository: IRepository
) : PagingSource<Int, RespAttractionsAll.Attraction>() {

    override fun getRefreshKey(state: PagingState<Int, RespAttractionsAll.Attraction>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RespAttractionsAll.Attraction> {
        return try {
            val page = params.key ?: 1
            val response = repository.getAllAttractions(page)

            when (response) {
                is NetworkResponse.Success -> {
                    LoadResult.Page(
                        data = response.data.attractions,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (response.data.attractions.isEmpty()) null else page + 1
                    )
                }
                is NetworkResponse.Error -> {
                    LoadResult.Error(Exception(response.errorMessage))
                }
                is NetworkResponse.Exception -> {
                    LoadResult.Error(response.exception)
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
} 