package com.example.bookshelf.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.bookshelf.common.IoDispatcher
import com.example.bookshelf.common.NetworkChecker
import com.example.bookshelf.data.dto.ApiException
import com.example.bookshelf.domain.entity.GetNewResponseEntity
import com.example.bookshelf.domain.usecase.ResultUseCase
import com.example.bookshelf.presentation.model.Book
import com.example.bookshelf.presentation.model.toBookList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewScreenViewModel @Inject constructor(
    networkChecker: NetworkChecker,
    private val getNewUseCase: ResultUseCase<Unit, GetNewResponseEntity>,
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher
) : ApiViewModel(networkChecker, coroutineDispatcher) {

    private val _newBookList = MutableSharedFlow<List<Book>>()
    val newBookList = _newBookList

    private val _isFinish = MutableStateFlow(false)
    val isFinish = _isFinish.asStateFlow()

    private val backPressEvent = MutableSharedFlow<Unit>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        viewModelScope.launch(coroutineDispatcher) {
            backPressEvent
                .scan(listOf(System.currentTimeMillis() - 1000L)) { acc, _ ->
                    acc.takeLast(1) + System.currentTimeMillis()
                }
                .drop(1)
                .collectLatest {
                    if (it.last() - it.first() < 1000L) {
                        _isFinish.emit(true)
                    }
                }
        }
    }

    fun pressBack() {
        backPressEvent.tryEmit(Unit)
    }

    fun getNew() {
        suspend fun getRes() {
            getNewUseCase(Unit)
                .onStart { /* TODO : Loading Start */ }
                .onCompletion { /* TODO : Loading End */ }
                .collect { res ->
                    res.onSuccess { entity ->
                        _newBookList.emit(entity.toBookList())
                    }.onFailure {
                        val errorCode = (it as ApiException).code

                        // TODO : Error
                    }
                }
        }

        apiWithCheckNetwork(::getRes)
    }

}