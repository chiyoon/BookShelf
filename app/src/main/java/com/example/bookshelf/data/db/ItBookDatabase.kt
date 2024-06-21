package com.example.bookshelf.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ItBookEntity::class, ItBookDetailEntity::class], version = 1)
abstract class ItBookDatabase : RoomDatabase() {

    abstract fun itBookDao(): ItBookDAO

    abstract fun itBookDetailDao(): ItBookDetailDAO

}