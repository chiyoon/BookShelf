package com.example.bookshelf.domain.respository

import androidx.paging.PagingData
import com.example.bookshelf.domain.entity.GetBooksRequestEntity
import com.example.bookshelf.domain.entity.GetBooksResponseEntity
import com.example.bookshelf.domain.entity.GetNewResponseEntity
import com.example.bookshelf.domain.entity.GetSearchRequestEntity
import com.example.bookshelf.domain.entity.GetSearchResponseEntity
import kotlinx.coroutines.flow.Flow

interface ItBookRepository {

    fun getNew(): Flow<Result<GetNewResponseEntity>>

    fun getBooks(requestEntity: GetBooksRequestEntity): Flow<Result<GetBooksResponseEntity>>

    fun getSearch(request: GetSearchRequestEntity): Flow<PagingData<GetSearchResponseEntity.Book>>

}