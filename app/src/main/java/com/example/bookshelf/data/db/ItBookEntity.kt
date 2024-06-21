package com.example.bookshelf.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "it_book")
data class ItBookEntity(
    @PrimaryKey val isbn13: String,
    val title: String,
    val subTitle: String,
    val imageUrl: String,
    val url: String,
    val price: String
)