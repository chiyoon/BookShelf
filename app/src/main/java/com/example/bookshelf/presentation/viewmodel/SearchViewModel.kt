package com.example.bookshelf.presentation.viewmodel

import com.example.bookshelf.common.IoDispatcher
import com.example.bookshelf.common.NetworkChecker
import com.example.bookshelf.data.dto.ApiException
import com.example.bookshelf.domain.entity.GetNewResponseEntity
import com.example.bookshelf.domain.entity.GetSearchRequestEntity
import com.example.bookshelf.domain.entity.GetSearchResponseEntity
import com.example.bookshelf.domain.usecase.ResultUseCase
import com.example.bookshelf.presentation.model.Book
import com.example.bookshelf.presentation.model.toBookList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchUseCase: ResultUseCase<GetSearchRequestEntity, GetSearchResponseEntity>,
    networkChecker: NetworkChecker,
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher
) : ApiViewModel(networkChecker, coroutineDispatcher) {

    private val _searchBookList = MutableSharedFlow<List<Book>>()
    val searchBookList = _searchBookList

    fun getSearch() {
        suspend fun getRes() {
            getSearchUseCase(GetSearchRequestEntity("query", 1))
                .onStart { /* TODO : Loading Start */ }
                .onCompletion { /* TODO : Loading End */ }
                .collect { res ->
                    res.onSuccess { entity ->
                        _searchBookList.emit(entity.toBookList())
                    }.onFailure {
                        val errorCode = (it as ApiException).code

                        // TODO : Error
                    }
                }
        }

        apiWithCheckNetwork(::getRes)
    }

}