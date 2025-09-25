package com.example.translationapp.di

import android.content.Context
import com.example.translationapp.domain.TranslationsListRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TranslationListModule {

    @Provides
    @Singleton
    fun provideTranslationsRepository(
        context: Context,
    ): TranslationsListRepository {
        return TranslationsListRepository(context)
    }
}