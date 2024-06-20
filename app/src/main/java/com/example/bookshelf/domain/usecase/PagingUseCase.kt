package com.example.bookshelf.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface PagingUseCase<R, T : Any> {

    suspend operator fun invoke(request: R): Flow<PagingData<T>>

}