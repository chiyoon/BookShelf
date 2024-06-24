package com.example.bookshelf.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.bookshelf.common.IoDispatcher
import com.example.bookshelf.common.NetworkChecker
import com.example.bookshelf.domain.entity.GetSearchRequestEntity
import com.example.bookshelf.domain.entity.GetSearchResponseEntity
import com.example.bookshelf.domain.usecase.PagingUseCase
import com.example.bookshelf.presentation.model.Book
import com.example.bookshelf.presentation.model.toBook
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchUseCase: PagingUseCase<GetSearchRequestEntity, GetSearchResponseEntity.Book>,
    savedStateHandle: SavedStateHandle,
    networkChecker: NetworkChecker,
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher
) : ApiLoadingViewModel(networkChecker, coroutineDispatcher) {

    private val _searchBookList = MutableStateFlow<PagingData<Book>>(PagingData.empty())
    val searchBookList = _searchBookList.asStateFlow()

    private val _query = MutableStateFlow(savedStateHandle.get<String>("query") ?: "")
    val query = _query.asStateFlow()

    init {
        savedStateHandle.get<String>("query")?.let { query ->
            if (query.isNotEmpty()) {
                getSearch(query)
            }
        }
    }

    fun getSearch(query: String) {
        suspend fun getRes() {
            _searchBookList.emit(PagingData.empty())

            getSearchUseCase(GetSearchRequestEntity(query, 1))
                .cachedIn(viewModelScope)
                .onStart { _isLoading.emit(true) }
                .collectLatest { pagingData ->
                    _searchBookList.emit(pagingData.map { it.toBook() })
                    _isLoading.emit(false)
                }
        }

        apiWithCheckNetwork(::getRes)
    }

}