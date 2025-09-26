package com.example.translationapp.ui.views.recycler_view

import com.example.translationapp.ui.entities.TranslationDetailsData

interface TranslationRecyclerAdapterActionListener {
    fun onTranslationLike(translation: TranslationDetailsData)
    fun onTranslationRemove(translation: TranslationDetailsData)
}