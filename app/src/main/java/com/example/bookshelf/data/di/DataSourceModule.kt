package com.example.bookshelf.data.di

import com.example.bookshelf.data.datasource.ItBookDataSource
import com.example.bookshelf.data.datasource.ItBookDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindItBookDataSource(itBookDataSourceImpl: ItBookDataSourceImpl): ItBookDataSource

}