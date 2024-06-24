package com.example.bookshelf.domain.usecase

import com.example.bookshelf.domain.entity.UpdateBookMemoRequestEntity
import com.example.bookshelf.domain.respository.ItBookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateBookMemoUseCase @Inject constructor(
    private val itBookRepository: ItBookRepository
) : ResultUseCase<UpdateBookMemoRequestEntity, Unit> {

    override suspend fun invoke(request: UpdateBookMemoRequestEntity): Flow<Result<Unit>> = itBookRepository.updateBookMemo(request)

}