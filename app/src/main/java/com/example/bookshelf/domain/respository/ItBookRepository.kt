package com.example.bookshelf.domain.respository

import com.example.bookshelf.domain.entity.GetNewResponseEntity
import kotlinx.coroutines.flow.Flow

interface ItBookRepository {

    fun getNew(): Flow<Result<GetNewResponseEntity>>

}