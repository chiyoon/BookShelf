package com.example.bookshelf.domain.usecase

import kotlinx.coroutines.flow.Flow

interface ResultUseCase<R, T> {

    suspend operator fun invoke(request: R): Flow<Result<T>>

}