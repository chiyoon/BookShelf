package com.example.bookshelf.ui.Model

import com.example.bookshelf.domain.entity.GetNewResponseEntity

data class Book(
    val title: String,
    val subtitle: String,
    val isbn13: String,
    val price: String,
    val image: String,
    val url: String
)

private fun GetNewResponseEntity.Book.toBook() = Book(
    this.title, this.subtitle, this.isbn13, this.price, this.image, this.url
)

fun GetNewResponseEntity.toBookList() = this.books.map { it.toBook() }