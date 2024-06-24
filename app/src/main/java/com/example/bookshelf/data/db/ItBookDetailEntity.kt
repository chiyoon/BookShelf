package com.example.bookshelf.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "it_book_detail",
    foreignKeys = [
        ForeignKey(
            entity = ItBookEntity::class,
            parentColumns = arrayOf("isbn13"),
            childColumns = arrayOf("isbn13"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ItBookDetailEntity(
    @PrimaryKey val isbn13: String,
    val isbn10: String,
    val rating: Int,
    val year: Int,
    val author: String,
    val publisher: String,
    val language: String,
    val description: String,
    val memo: String = ""
)