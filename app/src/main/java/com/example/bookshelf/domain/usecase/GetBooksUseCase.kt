package com.example.bookshelf.domain.usecase

import com.example.bookshelf.domain.entity.GetBooksRequestEntity
import com.example.bookshelf.domain.entity.GetBooksResponseEntity
import com.example.bookshelf.domain.respository.ItBookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBooksUseCase @Inject constructor(
    private val itBookRepository: ItBookRepository
) : ResultUseCase<GetBooksRequestEntity, GetBooksResponseEntity> {

    override suspend fun invoke(request: GetBooksRequestEntity): Flow<Result<GetBooksResponseEntity>> = itBookRepository.getBooks(request)

}