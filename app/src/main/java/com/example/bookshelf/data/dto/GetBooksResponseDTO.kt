package com.example.bookshelf.data.dto

import com.example.bookshelf.domain.entity.GetBooksResponseEntity

data class GetBooksResponseDTO(
    val error: Int,
    val title: String,
    val subtitle: String,
    val authors: String,
    val publisher: String,
    val language: String,
    val isbn10: String,
    val isbn13: String,
    val pages: Int,
    val year: Int,
    val rating: Int,
    val desc: String,
    val price: String,
    val image: String,
    val url: String,
    val pdf: Map<String, String>?
) {

    fun toDTO() = GetBooksResponseEntity(
        error, title, subtitle, authors, publisher, language, isbn10, isbn13, pages, year, rating, desc, price, image, url, pdf
    )

}