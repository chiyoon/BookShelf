package com.example.bookshelf.data.datasource

import com.example.bookshelf.data.db.ItBookDetailEntity
import com.example.bookshelf.data.db.ItBookEntity
import com.example.bookshelf.data.dto.GetBooksResponseDTO
import com.example.bookshelf.data.dto.GetNewResponseDTO
import com.example.bookshelf.data.dto.GetSearchResponseDTO
import kotlinx.coroutines.flow.Flow

interface ItBookDataSource {

    interface Remote {

        fun getNew(): Flow<Result<GetNewResponseDTO>>

        fun getBooks(isbn13: String): Flow<Result<GetBooksResponseDTO>>

        suspend fun getSearch(query: String, page: Int): Result<GetSearchResponseDTO>

    }

    interface Local {

        suspend fun insertItBooks(itBookEntityList: List<ItBookEntity>)

        suspend fun getItBookDetail(isbn13: String): ItBookDetailEntity?

        suspend fun insertItBookDetail(itBookDetail: ItBookDetailEntity)

    }

}