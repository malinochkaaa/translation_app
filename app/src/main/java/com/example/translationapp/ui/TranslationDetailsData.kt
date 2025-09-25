package com.example.translationapp.ui

data class TranslationDetailsData(
    val translationId: Long,
    val isFavorite: Boolean,
    val searchedWord: String,
    val translatedWord: String,
)