package com.example.bookshelf.data.di

import com.example.bookshelf.data.repository.ItBookRepositoryImpl
import com.example.bookshelf.domain.respository.ItBookRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindItBookRepository(itBookRepositoryImpl: ItBookRepositoryImpl): ItBookRepository

}