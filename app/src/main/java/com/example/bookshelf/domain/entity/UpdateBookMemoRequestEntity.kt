package com.example.bookshelf.domain.entity

data class UpdateBookMemoRequestEntity(
    val isbn13: String,
    val memo: String
)