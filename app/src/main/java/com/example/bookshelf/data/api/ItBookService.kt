package com.example.bookshelf.data.api

import com.example.bookshelf.data.dto.GetNewResponseDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface ItBookService {

    @GET("new")
    suspend fun getNew(): GetNewResponseDTO

    @GET("books/{isbn}")
    suspend fun getBooks(
        @Path("isbn") isbn: String
    )

    @GET("search/{query}")
    suspend fun getSearch(
        @Path("query") query: String
    )

}