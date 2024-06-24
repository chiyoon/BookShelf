package com.example.bookshelf.data.datasource

import com.example.bookshelf.data.db.ItBookDAO
import com.example.bookshelf.data.db.ItBookDetailDAO
import com.example.bookshelf.data.db.ItBookDetailEntity
import com.example.bookshelf.data.db.ItBookEntity
import javax.inject.Inject

class ItBookLocalDataSourceImpl @Inject constructor(
    private val itBookDAO: ItBookDAO,
    private val itBookDetailDAO: ItBookDetailDAO
) : ItBookDataSource.Local {

    override suspend fun insertItBooks(itBookEntityList: List<ItBookEntity>) {
        itBookDAO.insetItBookList(itBookEntityList)
    }

    override suspend fun getItBookDetail(isbn13: String): ItBookDetailEntity? = itBookDetailDAO.getBookDetail(isbn13)

    override suspend fun insertItBookDetail(itBookDetail: ItBookDetailEntity) {
        itBookDetailDAO.insertBookDetail(itBookDetail)
    }
}