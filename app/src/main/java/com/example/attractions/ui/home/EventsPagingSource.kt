package com.example.attractions.ui.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.attractions.model.event.RespEventsNews
import com.example.attractions.repository.IRepository
import com.example.attractions.repository.NetworkResponse

class EventsPagingSource(
    private val repository: IRepository,
    private val begin: String,
    private val end: String
) : PagingSource<Int, RespEventsNews.Event>() {

    override fun getRefreshKey(state: PagingState<Int, RespEventsNews.Event>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RespEventsNews.Event> {
        return try {
            val page = params.key ?: 1
            val response = repository.getNewsEvents(begin, end, page)

            when (response) {
                is NetworkResponse.Success -> {
                    LoadResult.Page(
                        data = response.data.events,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (response.data.events.isEmpty()) null else page + 1
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