package com.example.bookshelf.data.dto

import com.example.bookshelf.domain.entity.GetSearchResponseEntity

data class GetSearchResponseDTO(
    val error: Int,
    val total: Int,
    val page: Int,
    val books: List<Book>
) {
    fun toEntity() = GetSearchResponseEntity(
        error,
        total,
        page,
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
        fun toEntity() = GetSearchResponseEntity.Book(
            title, subtitle, isbn13, price, image, url
        )
    }

}