package com.example.translationapp.presentation

data class TranslationDetailsData(
    val translationId: Long,
    val isFavorite: Boolean,
    val searchedWord: String,
    val translatedWord: String,
)

interface TranslationActionListener {
    fun onTranslationLike(translation: TranslationDetailsData)
    fun onTranslationRemove(translation: TranslationDetailsData)
}