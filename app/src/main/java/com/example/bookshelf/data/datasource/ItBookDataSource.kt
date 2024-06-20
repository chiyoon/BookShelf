package com.example.bookshelf.data.datasource

import com.example.bookshelf.data.dto.GetNewResponseDTO
import com.example.bookshelf.data.dto.GetSearchResponseDTO
import kotlinx.coroutines.flow.Flow

interface ItBookDataSource {

    fun getNew(): Flow<Result<GetNewResponseDTO>>

    fun getSearch(query: String, page: Int): Flow<Result<GetSearchResponseDTO>>

}