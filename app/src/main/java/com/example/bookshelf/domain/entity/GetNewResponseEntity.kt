package com.example.bookshelf.domain.entity

data class GetNewResponseEntity(
    val error: Int,
    val total: Int,
    val books: List<Book>
) {
    data class Book(
        val title: String,
        val subtitle: String,
        val isbn13: String,
        val price: String,
        val image: String,
        val url: String
    )
}

