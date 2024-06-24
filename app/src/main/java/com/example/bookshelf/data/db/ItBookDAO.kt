package com.example.bookshelf.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ItBookDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItBook(book: ItBookEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insetItBookList(itBookList: List<ItBookEntity>)

    @Query("select * from it_book")
    suspend fun getAllItBook(): List<ItBookEntity>

}