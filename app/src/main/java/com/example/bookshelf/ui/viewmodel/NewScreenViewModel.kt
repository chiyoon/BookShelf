package com.example.bookshelf.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookshelf.data.api.ItBookClient
import com.example.bookshelf.data.datasource.ItBookDataSource
import com.example.bookshelf.data.dto.ApiException
import com.example.bookshelf.domain.respository.ItBookRepository
import com.example.bookshelf.domain.usecase.GetNewUseCase
import com.example.bookshelf.ui.Model.Book
import com.example.bookshelf.ui.Model.toBookList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.subscribeOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewScreenViewModel @Inject constructor(
    private val getNewUseCase: GetNewUseCase
) : ViewModel() {

    private val _newBookList = MutableSharedFlow<List<Book>>()
    val newBookList = _newBookList

    fun getRes() {
        viewModelScope.launch(Dispatchers.IO) {
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
    }

}