package com.example.bookshelf.data.di

import android.content.Context
import androidx.room.Room
import com.example.bookshelf.data.db.ItBookDAO
import com.example.bookshelf.data.db.ItBookDatabase
import com.example.bookshelf.data.db.ItBookDetailDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun providesItBookDAO(itBookDatabase: ItBookDatabase): ItBookDAO = itBookDatabase.itBookDao()

    @Provides
    fun providesItBookDetailDAO(itBookDatabase: ItBookDatabase): ItBookDetailDAO =
        itBookDatabase.itBookDetailDao()

    @Provides
    @Singleton
    fun providesItBookDatabase(@ApplicationContext context: Context): ItBookDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            ItBookDatabase::class.java,
            "itbook_database"
        ).build()

}