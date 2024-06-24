package com.example.bookshelf.data.datasource

import com.example.bookshelf.data.api.ItBookClient
import com.example.bookshelf.data.dto.ApiException
import com.example.bookshelf.data.dto.GetBooksResponseDTO
import com.example.bookshelf.data.dto.GetNewResponseDTO
import com.example.bookshelf.data.dto.GetSearchResponseDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class ItBookRemoteDataSourceImpl @Inject constructor() : ItBookDataSource.Remote {

    override fun getNew(): Flow<Result<GetNewResponseDTO>> {
        return flow {
            try {
                val res = ItBookClient.itBookService.getNew()

                emit(Result.success(res))
            } catch (e: HttpException) {
                emit(Result.failure(ApiException(e.code())))
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }
    }

    override fun getBooks(isbn13: String): Flow<Result<GetBooksResponseDTO>> {
        return flow {
            try {
                val res = ItBookClient.itBookService.getBooks(isbn13)

                emit(Result.success(res))
            } catch (e: HttpException) {
                emit(Result.failure(ApiException(e.code())))
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }
    }

    override suspend fun getSearch(query: String, page: Int): Result<GetSearchResponseDTO> =
        try {
            val res = ItBookClient.itBookService.getSearch(query, page)

            Result.success(res)
        } catch(e: HttpException) {
            Result.failure(ApiException(e.code()))
        } catch (e: Exception) {
            Result.failure(e)
        }
}