package com.example.bookshelf.data.datasource

import com.example.bookshelf.data.dto.GetNewResponseDTO
import kotlinx.coroutines.flow.Flow

interface ItBookDataSource {

    fun getNew(): Flow<Result<GetNewResponseDTO>>

}