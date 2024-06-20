package com.example.bookshelf.data.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ItBookClient {

    private const val BASE_URL = "https://api.itbook.store/"
    private const val VERSION = "1.0"

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val itBookClient = Retrofit.Builder()
        .baseUrl("$BASE_URL$VERSION/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val itBookService: ItBookService = itBookClient.create(ItBookService::class.java)

}