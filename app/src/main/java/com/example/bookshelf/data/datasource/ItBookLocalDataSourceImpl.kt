package com.example.bookshelf.data.datasource

import com.example.bookshelf.data.db.ItBookDAO
import com.example.bookshelf.data.db.ItBookDetailDAO
import com.example.bookshelf.data.db.ItBookEntity
import javax.inject.Inject

class ItBookLocalDataSourceImpl @Inject constructor(
    private val itBookDAO: ItBookDAO,
    private val itBookDetailDAO: ItBookDetailDAO
) : ItBookDataSource.Local {

    override suspend fun insertItBooks(itBookEntityList: List<ItBookEntity>) {
        println("IT Book Insert!")
        itBookDAO.insetItBookList(itBookEntityList)
    }

}