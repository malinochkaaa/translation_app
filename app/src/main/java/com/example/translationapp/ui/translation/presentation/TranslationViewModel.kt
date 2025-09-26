package com.example.translationapp.ui.translation.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.translationapp.R
import com.example.translationapp.di.TranslationFeatureComponent
import com.example.translationapp.domain.TranslationsListRepository
import com.example.translationapp.ui.translation.domain.GetTranslationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TranslationViewModel : ViewModel() {

    @Inject
    lateinit var translationsListRepository: TranslationsListRepository

    @Inject
    lateinit var getTranslationUseCase: GetTranslationUseCase

    private val _translationViewState = MutableStateFlow(
        TranslationViewState(
            foundWordText = null,
            errorMessage = null,
        )
    )
    private var translationViewStateValue
        get() = _translationViewState.value
        set(value) {_translationViewState.value = value }
    val translationViewState: Flow<TranslationViewState> = _translationViewState

    init {
        TranslationFeatureComponent.instance.injectTranslationViewModel(this)
    }

    fun onButtonClicked(searchedWord:String) {
        viewModelScope.launch(Dispatchers.IO) {
            val translatedWord = getTranslationUseCase.getWordTranslationData(searchedWord)
            if (translatedWord == null) {
                withContext(Dispatchers.Default) {
                    translationViewStateValue = copyValueWithError(R.string.error_message_text_failed_to_translate)
                }
            } else {
                withContext(Dispatchers.Default) {
                    translationViewStateValue = copyValueWithFoundWordText(translatedWord)
                }
                insertTranslation(searchedWord, translatedWord)
                    .onFailure {
                        translationViewStateValue = copyValueWithError(R.string.error_message_text_failed_to_add)
                    }
            }
        }
    }

    private fun insertTranslation(searchedWord: String, translatedWord: String) =
        translationsListRepository.insertTranslation(
            searchedWord = searchedWord,
            translatedWord = translatedWord,
            isFavorite = false,
        )
    private fun copyValueWithFoundWordText(foundWord: String) =
        translationViewStateValue.copy(
            foundWordText = foundWord,
            errorMessage = null,
        )
    private fun copyValueWithError(errorMessage: Int) =
        translationViewStateValue.copy(
            foundWordText = null,
            errorMessage = errorMessage,
        )
}