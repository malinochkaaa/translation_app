package com.example.translationapp.di

import com.example.translationapp.ui.translation.data.TranslationApi
import com.example.translationapp.ui.translation.domain.GetTranslationUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GetTranslationModule {

    @Provides
    @Singleton
    fun provideGetTranslationUseCase(
        translationApi: TranslationApi,
    ): GetTranslationUseCase {
        return GetTranslationUseCase(translationApi)
    }
}