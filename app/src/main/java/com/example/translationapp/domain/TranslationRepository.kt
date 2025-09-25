package com.example.translationapp.domain

import android.content.Context
import com.example.translationapp.data.TranslationEntity
import com.example.translationapp.data.TranslationsDatabase
import kotlinx.coroutines.flow.Flow

class TranslationsListRepository(
    context: Context,
) {
    private val translationsDatabase = TranslationsDatabase.getTranslationsDatabase(context)
    private val translationsDao = translationsDatabase.getTranslationsDao()

    fun observeFavorites(
        searchedWord: String,
        translatedWord: String,
        isFavorite: Boolean,
    ) : Result<Flow<List<TranslationEntity>>> = runCatching {
        translationsDao.observeFavorites()
    }

    fun observeTranslations(
        searchedWord: String,
        translatedWord: String,
        isFavorite: Boolean,
    ) : Result<Flow<List<TranslationEntity>>> = runCatching {
        translationsDao.observeTranslations()
    }

    fun insertTranslation(
        searchedWord: String,
        translatedWord: String,
        isFavorite: Boolean,
    ) : Result<Unit> = runCatching {
        translationsDao.insertTranslation(
            TranslationEntity(
                searchedWord = searchedWord,
                translatedWord = translatedWord,
                isFavorite = isFavorite,
            )
        )
    }

    fun updateTranslation(
        id: Long,
        searchedWord: String,
        translatedWord: String,
        isFavorite: Boolean,
    ) : Result<Unit> = runCatching {
        translationsDao.updateTranslation(
            TranslationEntity(
                id = id,
                searchedWord = searchedWord,
                translatedWord = translatedWord,
                isFavorite = isFavorite,
            )
        )
    }
}