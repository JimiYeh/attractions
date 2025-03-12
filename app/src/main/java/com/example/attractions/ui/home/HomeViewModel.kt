package com.example.attractions.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.attractions.model.attraction.RespAttractionsAll
import com.example.attractions.model.event.RespEventsNews
import com.example.attractions.repository.IRepository
import com.example.attractions.repository.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HomeViewModel(
    private val repository: IRepository
) : ViewModel() {

    private val _attractionsCount = MutableLiveData<Int>()
    val attractionsCount: LiveData<Int> = _attractionsCount

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    // 活動列表
    val events: Flow<PagingData<RespEventsNews.Event>> = Pager(
        config = PagingConfig(
            pageSize = 30,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            EventsPagingSource(
                repository = repository,
                // 取得最近五年的活動
                begin = LocalDate.now().minusYears(5).format(dateFormatter),
                end = LocalDate.now().format(dateFormatter)
            )
        }
    ).flow.cachedIn(viewModelScope)

    // 景點列表
    val attractions: Flow<PagingData<RespAttractionsAll.Attraction>> = Pager(
        config = PagingConfig(
            pageSize = 30,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { AttractionsPagingSource(repository) }
    ).flow.cachedIn(viewModelScope)

    init {
        loadAttractionsCount()
    }

    private fun loadAttractionsCount() {
        viewModelScope.launch {
            val response = repository.getAllAttractions(1)
            if (response.isSuccess) {
                _attractionsCount.value = (response as NetworkResponse.Success).data.total
            }
        }
    }
}