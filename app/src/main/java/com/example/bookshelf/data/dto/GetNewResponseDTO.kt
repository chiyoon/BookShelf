package com.example.bookshelf.data.dto

import com.example.bookshelf.domain.entity.GetNewResponseEntity

data class GetNewResponseDTO(
    val error: Int,
    val total: Int,
    val books: List<Book>
) {
    fun toEntity() = GetNewResponseEntity(
        error,
        total,
        books.map { it.toEntity() }
    )

    data class Book(
        val title: String,
        val subtitle: String,
        val isbn13: String,
        val price: String,
        val image: String,
        val url: String
    ) {
        fun toEntity() = GetNewResponseEntity.Book(
            title, subtitle, isbn13, price, image, url
        )
    }

}