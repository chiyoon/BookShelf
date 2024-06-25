package com.example.bookshelf.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookshelf.common.NetworkChecker
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction0

abstract class ApiViewModel(
    private val networkChecker: NetworkChecker,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _isConnected: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()

    fun apiWithCheckNetwork(apiCall: KSuspendFunction0<Unit>) {
        viewModelScope.launch(coroutineDispatcher) {
            if (networkChecker.isConnected()) {
                _isConnected.emit(true)
                apiCall.invoke()
            } else {
                _isConnected.emit(false)
            }
        }
    }

    fun cachedApiWithCheckNetwork(apiCall: KSuspendFunction0<Unit>) {
        viewModelScope.launch(coroutineDispatcher) {
            if (networkChecker.isConnected()) {
                _isConnected.emit(true)
            } else {
                _isConnected.emit(false)
            }

            apiCall.invoke()
        }
    }

}