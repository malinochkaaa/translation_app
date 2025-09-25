package com.example.translationapp.presentation.translation.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.translationapp.domain.TranslationsRepository
import com.example.translationapp.presentation.TranslationDetailsData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TranslationViewModel : ViewModel() {

    lateinit var translationsRepository: TranslationsRepository

    private val _homeState = MutableStateFlow(
        TranslationDetailsData(
            calendarData = null,
            weatherData = null,
        )
    )
    private var homeStateValue
        get() = _homeState.value
        set(value) {_homeState.value = value }
    val homeState: Flow<TranslationDetailsData> = _homeState

    fun onButtonClicked(searchedWord:String) {
        viewModelScope.launch(Dispatchers.IO) {
            val translatedWord = translationsRepository.getWordTranslationData(searchedWord)
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
        return translationsRepository.insertTranslation(
            searchedWord = searchedWord,
            translatedWord = translatedWord,
            isFavorite = false,
        )
    }
    val text: LiveData<String> = _text
}