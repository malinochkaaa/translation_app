package com.example.translationapp.ui.translation.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.translationapp.domain.TranslationsListRepository
import com.example.translationapp.ui.TranslationDetailsData
import com.example.translationapp.ui.translation.domain.GetTranslationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

class TranslationViewModel : ViewModel() {

    @Inject
    lateinit var translationsListRepository: TranslationsListRepository

    @Inject
    lateinit var getTranslationUseCase: GetTranslationUseCase

    private val _translationDetailsState = MutableStateFlow<TranslationDetailsData?>(null)
    private var translationDetailsStateValue
        get() = _translationDetailsState.value
        set(value) {_translationDetailsState.value = value }
    val translationDetailsState: Flow<TranslationDetailsData?> = _translationDetailsState.filterNotNull()

    fun onButtonClicked(searchedWord:String) {
        viewModelScope.launch(Dispatchers.IO) {
            val translatedWord = getTranslationUseCase.getWordTranslationData(searchedWord)
            if (translatedWord == null) {
                Log.e("Lol", "Error")
            } else {
                insertTranslation(searchedWord, translatedWord)
                    .onFailure {
                        Log.e("Lol", "Error")
                    }
                    .onSuccess {

                    }
            }
        }
    }

    suspend fun insertTranslation(searchedWord: String, translatedWord: String) : Result<Unit> {
        return translationsListRepository.insertTranslation(
            searchedWord = searchedWord,
            translatedWord = translatedWord,
            isFavorite = false,
        )
    }
}