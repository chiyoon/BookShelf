package com.example.bookshelf.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ItBookDetailDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBookDetail(detail: ItBookDetailEntity)

    @Query("SELECT * FROM it_book_detail WHERE isbn13 = :isbn13")
    suspend fun getBookDetail(isbn13: String): ItBookDetailEntity?

    @Query("UPDATE it_book_detail SET memo = :memo WHERE isbn13 = :isbn13")
    suspend fun updateBookMemo(isbn13: String, memo: String): Int

}