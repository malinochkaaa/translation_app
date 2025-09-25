package com.example.translationapp.ui

import androidx.annotation.StringRes

interface BaseViewAction {
    data class ShowToastError(@StringRes val errorMessage: Int) : BaseViewAction
}