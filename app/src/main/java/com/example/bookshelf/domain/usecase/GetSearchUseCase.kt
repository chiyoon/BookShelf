package com.example.bookshelf.domain.usecase

import androidx.paging.PagingData
import com.example.bookshelf.domain.entity.GetSearchRequestEntity
import com.example.bookshelf.domain.entity.GetSearchResponseEntity
import com.example.bookshelf.domain.respository.ItBookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchUseCase @Inject constructor(
    private val itBookRepository: ItBookRepository
) : PagingUseCase<GetSearchRequestEntity, GetSearchResponseEntity.Book> {

    override suspend fun invoke(request: GetSearchRequestEntity): Flow<PagingData<GetSearchResponseEntity.Book>> = itBookRepository.getSearch(request)

}