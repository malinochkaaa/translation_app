package com.example.translationapp.domain

import com.example.translationapp.data.TranslationEntity
import com.example.translationapp.data.TranslationsDao
import com.example.translationapp.ui.entities.TranslationDetailsData

class TranslationsListRepository(
    private val translationsDao: TranslationsDao
) {

    fun observeFavorites() = translationsDao.observeFavorites()

    fun observeTranslations() = translationsDao.observeTranslations()

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
        translationData: TranslationDetailsData,
    ) : Result<Unit> = runCatching {
        translationsDao.updateTranslation(
            translationData
                .copy(isFavorite = !translationData.isFavorite)
                .toTranslationEntity()
        )
    }

    fun removeTranslation(
        translationData: TranslationDetailsData,
    ) : Result<Unit> = runCatching {
        translationsDao.removeTranslation(
            translationData.toTranslationEntity()
        )
    }

    private fun TranslationDetailsData.toTranslationEntity() = TranslationEntity(
        id = this.translationId,
        isFavorite  = this.isFavorite,
        searchedWord = this.searchedWord,
        translatedWord = this.translatedWord,
    )
}