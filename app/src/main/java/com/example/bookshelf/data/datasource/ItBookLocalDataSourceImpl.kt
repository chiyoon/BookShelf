package com.example.bookshelf.data.datasource

import com.example.bookshelf.data.db.ItBookDAO
import com.example.bookshelf.data.db.ItBookDetailDAO
import com.example.bookshelf.data.db.ItBookDetailEntity
import com.example.bookshelf.data.db.ItBookEntity
import com.example.bookshelf.data.dto.ApiException
import javax.inject.Inject

class ItBookLocalDataSourceImpl @Inject constructor(
    private val itBookDAO: ItBookDAO,
    private val itBookDetailDAO: ItBookDetailDAO
) : ItBookDataSource.Local {

    override suspend fun insertItBooks(itBookEntityList: List<ItBookEntity>) {
        itBookDAO.insetItBookList(itBookEntityList)
    }

    override suspend fun getItBook(isbn13: String): ItBookEntity? = itBookDAO.getItBook(isbn13)

    override suspend fun getItBookDetail(isbn13: String): ItBookDetailEntity? = itBookDetailDAO.getBookDetail(isbn13)

    override suspend fun insertItBookDetail(itBookDetail: ItBookDetailEntity) {
        itBookDetailDAO.insertBookDetail(itBookDetail)
    }

    override suspend fun updateBookMemo(isbn13: String, memo: String): Result<Unit> {
        val isSuccess = itBookDetailDAO.updateBookMemo(isbn13, memo) != -1

        if (isSuccess) {
            return Result.success(Unit)
        } else {
            // TODO : API Exception 수정
            return Result.failure(ApiException(-1))
        }
    }

    override suspend fun searchItBook(query: String, page: Int): Result<List<ItBookEntity>> {
        val data = itBookDAO.searchItBook("%$query%", 10 * (page - 1))

        return Result.success(data)
    }
}