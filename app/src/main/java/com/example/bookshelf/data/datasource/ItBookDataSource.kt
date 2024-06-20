package com.example.bookshelf.data.datasource

import com.example.bookshelf.data.dto.GetBooksResponseDTO
import com.example.bookshelf.data.dto.GetNewResponseDTO
import com.example.bookshelf.data.dto.GetSearchResponseDTO
import kotlinx.coroutines.flow.Flow

interface ItBookDataSource {

    fun getNew(): Flow<Result<GetNewResponseDTO>>

    fun getBooks(isbn13: String): Flow<Result<GetBooksResponseDTO>>

    suspend fun getSearch(query: String, page: Int): Result<GetSearchResponseDTO>

}