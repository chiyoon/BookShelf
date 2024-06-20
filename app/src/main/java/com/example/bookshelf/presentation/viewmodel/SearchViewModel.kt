package com.example.bookshelf.presentation.viewmodel

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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchUseCase: PagingUseCase<GetSearchRequestEntity, GetSearchResponseEntity.Book>,
    networkChecker: NetworkChecker,
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher
) : ApiViewModel(networkChecker, coroutineDispatcher) {

    private val _searchBookList = MutableSharedFlow<PagingData<Book>>()
    val searchBookList = _searchBookList

    fun getSearch(query: String) {
        suspend fun getRes() {
            _searchBookList.emit(PagingData.empty())

            getSearchUseCase(GetSearchRequestEntity(query, 1))
                .cachedIn(viewModelScope)
                .onStart { /* TODO : Loading Start */ }
                .onCompletion { /* TODO : Loading End */ }
                .collectLatest { pagingData ->
                    _searchBookList.emit(pagingData.map { it.toBook() })
                }
        }

        apiWithCheckNetwork(::getRes)
    }

}