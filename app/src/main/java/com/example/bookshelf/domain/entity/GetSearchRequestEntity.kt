package com.example.bookshelf.domain.entity

data class GetSearchRequestEntity(
    val query: String,
    val page: Int
)