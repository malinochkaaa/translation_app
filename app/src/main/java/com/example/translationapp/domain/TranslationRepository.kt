package com.example.translationapp.domain

import android.content.Context
import com.example.translationapp.data.TranslationEntity
import com.example.translationapp.data.TranslationsDatabase
import com.example.translationapp.presentation.translation.data.TranslationApi
import kotlinx.coroutines.flow.Flow

class TranslationsRepository(
    context: Context,
    private val translationApi: TranslationApi,
) {
    private val translationsDatabase = TranslationsDatabase.getTranslationsDatabase(context)
    private val translationsDao = translationsDatabase.getTranslationsDao()

    suspend fun getWordTranslationData(searchWord: String): String? {
        val response = translationApi.getWordTranslation(searchWord)

        return if (response.isSuccessful) {
            val translationResponse = response.body()
            translationResponse?.let {
                it.searchData.firstOrNull()?.meanings?.firstOrNull()?.translation?.text
            }
        } else {
            null
        }
    }

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