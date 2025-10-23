package com.example.translationapp.ui.translation_feature.presentation

import androidx.annotation.StringRes

data class TranslationViewState(
    val foundWordText: String?,
    @StringRes val errorMessage: Int?,
)