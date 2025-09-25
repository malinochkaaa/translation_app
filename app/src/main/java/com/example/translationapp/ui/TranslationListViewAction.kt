package com.example.translationapp.ui

import androidx.annotation.StringRes

sealed interface TranslationListViewAction {
    data class ShowToastError(@StringRes val errorMessage: Int) : TranslationListViewAction
    data object NavigateToTranslationScreen : TranslationListViewAction
}