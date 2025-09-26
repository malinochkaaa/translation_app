package com.example.translationapp.di

import android.content.Context
import androidx.room.Room
import com.example.translationapp.data.TranslationsDao
import com.example.translationapp.data.TranslationsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        context: Context,
    ): TranslationsDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            TranslationsDatabase::class.java,
            "translations_database.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTranslationsDao(
        translationsDatabase: TranslationsDatabase,
    ): TranslationsDao {
        return translationsDatabase.getTranslationsDao()
    }

}