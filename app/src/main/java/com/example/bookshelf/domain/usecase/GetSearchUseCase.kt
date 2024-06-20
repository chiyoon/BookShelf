package com.example.bookshelf.domain.usecase

import com.example.bookshelf.domain.entity.GetSearchRequestEntity
import com.example.bookshelf.domain.entity.GetSearchResponseEntity
import com.example.bookshelf.domain.respository.ItBookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchUseCase @Inject constructor(
    private val itBookRepository: ItBookRepository
) : ResultUseCase<GetSearchRequestEntity, GetSearchResponseEntity> {

    override suspend fun invoke(request: GetSearchRequestEntity): Flow<Result<GetSearchResponseEntity>> = itBookRepository.getSearch(request)

}