package com.example.bookshelf.data.datasource

import com.example.bookshelf.data.api.ItBookClient
import com.example.bookshelf.data.dto.ApiException
import com.example.bookshelf.data.dto.GetNewResponseDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class ItBookDataSourceImpl @Inject constructor() : ItBookDataSource {

    override fun getNew(): Flow<Result<GetNewResponseDTO>> {
        return flow {
            try {
                val res = ItBookClient.itBookService.getNew()

                emit(Result.success(res))
            } catch (e: HttpException) {
                emit(Result.failure(ApiException(e.code())))
            }
        }
    }

}