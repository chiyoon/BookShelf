package com.example.bookshelf.presentation.viewmodel

import com.example.bookshelf.common.IoDispatcher
import com.example.bookshelf.common.NetworkChecker
import com.example.bookshelf.data.dto.ApiException
import com.example.bookshelf.domain.entity.GetBooksRequestEntity
import com.example.bookshelf.domain.entity.GetBooksResponseEntity
import com.example.bookshelf.domain.usecase.ResultUseCase
import com.example.bookshelf.presentation.model.BookDetail
import com.example.bookshelf.presentation.model.toBookDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    networkChecker: NetworkChecker,
    private val getBooksUseCase: ResultUseCase<GetBooksRequestEntity, GetBooksResponseEntity>,
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher
) : ApiViewModel(networkChecker, coroutineDispatcher) {

    private val _bookDetail = MutableSharedFlow<BookDetail>()
    val bookDetail = _bookDetail.asSharedFlow()

    private val _memo = MutableSharedFlow<String>()
    val memo = _memo.asSharedFlow()

    fun getBooks(isbn13: String) {
        suspend fun getRes() {
            getBooksUseCase(GetBooksRequestEntity(isbn13))
                .onStart { /* TODO : Loading Start */ }
                .onCompletion { /* TODO : Loading End */ }
                .collect { res ->
                    res.onSuccess { entity ->
                        _bookDetail.emit(entity.toBookDetail())
                    }.onFailure {
                        val errorCode = (it as ApiException).code

                        // TODO : Error
                    }
                }
        }

        apiWithCheckNetwork(::getRes)
    }

}