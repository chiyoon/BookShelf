package com.example.bookshelf.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.bookshelf.data.datasource.ItBookDataSource
import com.example.bookshelf.data.dto.ApiException
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

    override fun getSearch(request: GetSearchRequestEntity): Flow<PagingData<GetSearchResponseEntity.Book>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SearchPagingSource(request.query, itBookDataSource) }
        ).flow
    }

}

class SearchPagingSource(
    private val query: String,
    private val dataSource: ItBookDataSource
) : PagingSource<Int, GetSearchResponseEntity.Book>() {

    override fun getRefreshKey(state: PagingState<Int, GetSearchResponseEntity.Book>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetSearchResponseEntity.Book> {
        val page = params.key ?: 1
        val result = dataSource.getSearch(query, page)

        try {
            val data = result.getOrThrow()

            return LoadResult.Page(
                data = data.books.map { it.toEntity() },
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data.books.isEmpty()) null else page + 1
            )
        } catch (e: ApiException) {
            return LoadResult.Error(e)
        }
    }

}