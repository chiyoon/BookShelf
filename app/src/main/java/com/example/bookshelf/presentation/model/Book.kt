package com.example.bookshelf.presentation.model

import com.example.bookshelf.domain.entity.GetNewResponseEntity
import com.example.bookshelf.domain.entity.GetSearchResponseEntity

data class Book(
    val title: String,
    val subtitle: String,
    val isbn13: String,
    val price: String,
    val image: String,
    val url: String
) {

    companion object {

        val placeholder = Book("", "", "", "", "", "")

    }

}

private fun GetNewResponseEntity.Book.toBook() = Book(
    this.title, this.subtitle, this.isbn13, this.price, this.image, this.url
)

fun GetNewResponseEntity.toBookList() = this.books.map { it.toBook() }

fun GetSearchResponseEntity.Book.toBook() = Book(
    this.title, this.subtitle, this.isbn13, this.price, this.image, this.url
)

fun GetSearchResponseEntity.toBookList() = this.books.map { it.toBook() }