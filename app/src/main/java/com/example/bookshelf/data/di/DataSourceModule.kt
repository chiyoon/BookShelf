package com.example.bookshelf.data.di

import com.example.bookshelf.data.datasource.ItBookDataSource
import com.example.bookshelf.data.datasource.ItBookLocalDataSourceImpl
import com.example.bookshelf.data.datasource.ItBookRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindItBookRemoteDataSource(itBookRemoteDataSourceImpl: ItBookRemoteDataSourceImpl): ItBookDataSource.Remote

    @Binds
    abstract fun bindItBookLocalDataSource(itBookLocalDataSource: ItBookLocalDataSourceImpl): ItBookDataSource.Local

}