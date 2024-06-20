package com.example.bookshelf.domain.usecase

import com.example.bookshelf.domain.entity.GetNewResponseEntity
import com.example.bookshelf.domain.respository.ItBookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewUseCase @Inject constructor(
    private val itBookRepository: ItBookRepository
) : ResultUseCase<Unit, GetNewResponseEntity> {

    override suspend fun invoke(request: Unit): Flow<Result<GetNewResponseEntity>> = itBookRepository.getNew()

}