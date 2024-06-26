package com.example.bookshelf.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// TODO: Room 에서 조회시 데이터가 없는 경우 Null 보다 더 좋은 처리 방법은 없을까?
@Dao
interface ItBookDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItBook(book: ItBookEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insetItBookList(itBookList: List<ItBookEntity>)

    @Query("select * from it_book where isbn13 = :isbn13 limit 1")
    suspend fun getItBook(isbn13: String): ItBookEntity?

    @Query("select * from it_book where isbn13 like(:query) or title like(:query) or subTitle like(:query) limit 10 offset :offset")
    suspend fun searchItBook(query: String, offset: Int): List<ItBookEntity>

}