package com.example.bookshelf.data.repository

import com.example.bookshelf.data.datasource.ItBookDataSource
import com.example.bookshelf.domain.entity.GetNewResponseEntity
import com.example.bookshelf.domain.entity.GetSearchRequestEntity
import com.example.bookshelf.domain.entity.GetSearchResponseEntity
import com.example.bookshelf.domain.respository.ItBookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ItBookRepositoryImpl @Inject constructor(
    private val itBookDataSource: ItBookDataSource
) : ItBookRepository {

    override fun getNew(): Flow<Result<GetNewResponseEntity>> {
        return flow {
            itBookDataSource.getNew().collect { dto ->
                emit(dto.mapCatching { it.toEntity() })
            }
        }
    }

    override fun getSearch(request: GetSearchRequestEntity): Flow<Result<GetSearchResponseEntity>> {
        return flow {
            itBookDataSource.getSearch(request.query, request.page).collect { dto ->
                emit(dto.mapCatching { it.toEntity() })
            }
        }
    }

}