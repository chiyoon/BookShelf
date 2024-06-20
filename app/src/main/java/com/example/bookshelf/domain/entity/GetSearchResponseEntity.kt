package com.example.bookshelf.domain.entity

class GetSearchResponseEntity(
    val error: Int,
    val total: Int,
    val page: Int,
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