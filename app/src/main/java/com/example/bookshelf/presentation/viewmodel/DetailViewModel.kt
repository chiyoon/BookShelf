package com.example.bookshelf.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.bookshelf.common.IoDispatcher
import com.example.bookshelf.common.NetworkChecker
import com.example.bookshelf.data.dto.ApiException
import com.example.bookshelf.domain.entity.GetBooksRequestEntity
import com.example.bookshelf.domain.entity.GetBooksResponseEntity
import com.example.bookshelf.domain.entity.UpdateBookMemoRequestEntity
import com.example.bookshelf.domain.usecase.ResultUseCase
import com.example.bookshelf.presentation.model.BookDetail
import com.example.bookshelf.presentation.model.toBookDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    networkChecker: NetworkChecker,
    private val getBooksUseCase: ResultUseCase<GetBooksRequestEntity, GetBooksResponseEntity>,
    private val updateBookUseCase: ResultUseCase<UpdateBookMemoRequestEntity, Unit>,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : ApiLoadingViewModel(networkChecker, coroutineDispatcher) {

    private val _bookDetail = MutableSharedFlow<BookDetail>()
    val bookDetail = _bookDetail.asSharedFlow()

    private val _memo = MutableStateFlow("")
    val memo = _memo.asStateFlow()

    // TODO: Toast 등의 ui 작업을 위한 state 는 어떻게 저장되면 좋을지 학습 필요
    private val _isSaved = MutableSharedFlow<Boolean?>()
    val isSaved = _isSaved.asSharedFlow()

    private val _isNoCached = MutableStateFlow(false)
    val isNoCached = _isNoCached.asStateFlow()

    fun getBooks(isbn13: String) {
        suspend fun getRes() {
            getBooksUseCase(GetBooksRequestEntity(isbn13))
                .onStart { _isLoading.emit(true) }
                .onCompletion { _isLoading.emit(false) }
                .collect { res ->
                    res.onSuccess { entity ->
                        _bookDetail.emit(entity.toBookDetail())
                        _memo.emit(entity.memo)
                    }.onFailure {
                        val errorCode = (it as ApiException).code

                        if (errorCode == -1) {
                            _isNoCached.emit(true)
                        }
                    }
                }
        }

        cachedApiWithCheckNetwork(::getRes)
    }

    fun updateMemo(isbn13: String, memo: String) {
        viewModelScope.launch(coroutineDispatcher) {
            updateBookUseCase(UpdateBookMemoRequestEntity(isbn13, memo))
                .onStart { _isLoading.emit(true) }
                .onCompletion { _isLoading.emit(false) }
                .collect { res ->
                    res.onSuccess {
                        _isSaved.emit(true)
                    }.onFailure {
                        val errorCode = (it as ApiException).code

                        _isSaved.emit(false)
                    }
                }
        }
    }

}