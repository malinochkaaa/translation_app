package com.example.translationapp.ui.translation.presentation

import androidx.annotation.StringRes

data class TranslationViewState(
    val foundWordText: String?,
    @StringRes val errorMessage: Int?,
)