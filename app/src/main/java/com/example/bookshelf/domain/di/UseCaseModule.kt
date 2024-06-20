package com.example.bookshelf.domain.di

import com.example.bookshelf.domain.entity.GetNewResponseEntity
import com.example.bookshelf.domain.entity.GetSearchRequestEntity
import com.example.bookshelf.domain.entity.GetSearchResponseEntity
import com.example.bookshelf.domain.usecase.GetNewUseCase
import com.example.bookshelf.domain.usecase.GetSearchUseCase
import com.example.bookshelf.domain.usecase.PagingUseCase
import com.example.bookshelf.domain.usecase.ResultUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindGetNewUseCase(getNewUseCase: GetNewUseCase): ResultUseCase<Unit, GetNewResponseEntity>

    @Binds
    abstract fun bindGetSearchUseCase(getSearchUseCase: GetSearchUseCase): PagingUseCase<GetSearchRequestEntity, GetSearchResponseEntity.Book>

}