package com.example.translationapp.ui

interface TranslationRecyclerAdapterActionListener {
    fun onTranslationLike(translation: TranslationDetailsData)
    fun onTranslationRemove(translation: TranslationDetailsData)
}