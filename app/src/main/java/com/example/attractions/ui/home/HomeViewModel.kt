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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HomeViewModel(
    private val repository: IRepository
) : ViewModel() {

    private val _attractionsCount = MutableLiveData<Int>()
    val attractionsCount: LiveData<Int> = _attractionsCount

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    // 修改為 StateFlow，確保有初始值
    private val refreshTrigger = MutableStateFlow(0)

    // 活動列表
    val events: Flow<PagingData<RespEventsNews.Event>> = refreshTrigger.flatMapLatest {
        Pager(
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
        ).flow
    }.cachedIn(viewModelScope)

    // 景點列表
    val attractions: Flow<PagingData<RespAttractionsAll.Attraction>> = refreshTrigger.flatMapLatest {
        Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { AttractionsPagingSource(repository) }
        ).flow
    }.cachedIn(viewModelScope)

    init {
        loadAttractionsCount()
    }

    // 重新加載數據
    fun refresh() {
        viewModelScope.launch {
            refreshTrigger.emit(refreshTrigger.value + 1)  // 增加計數來觸發更新
            loadAttractionsCount()
        }
    }

    private fun loadAttractionsCount() {
        viewModelScope.launch {
            val response = repository.getAllAttractions(1)
            if (response.isSuccess) {
                _attractionsCount.value = (response as NetworkResponse.Success).data.total
            } else {
                _attractionsCount.value = 0
            }
        }
    }
}