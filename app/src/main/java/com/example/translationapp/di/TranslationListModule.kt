package com.example.translationapp.di

import com.example.translationapp.data.TranslationsDao
import com.example.translationapp.domain.TranslationsListRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TranslationListModule {

    @Provides
    @Singleton
    fun provideTranslationsRepository(
        translationsDao: TranslationsDao,
    ): TranslationsListRepository {
        return TranslationsListRepository(translationsDao)
    }
}