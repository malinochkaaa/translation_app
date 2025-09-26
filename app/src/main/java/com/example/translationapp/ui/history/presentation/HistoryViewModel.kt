package com.example.translationapp.ui.history.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.translationapp.R
import com.example.translationapp.di.TranslationFeatureComponent
import com.example.translationapp.domain.TranslationsListRepository
import com.example.translationapp.ui.actions.TranslationListViewAction
import com.example.translationapp.ui.entities.TranslationDetailsData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class HistoryViewModel : ViewModel() {

    @Inject
    lateinit var translationsListRepository: TranslationsListRepository
    var translationDetailsData: Flow<List<TranslationDetailsData>>
    private val _translationListViewAction = MutableSharedFlow<TranslationListViewAction>()
    val translationListViewAction: SharedFlow<TranslationListViewAction> =
        _translationListViewAction.asSharedFlow()

    init {
        TranslationFeatureComponent.instance.injectHistoryViewModel(this)
        translationDetailsData = observeTranslations()
    }

    fun onFavoriteButtonClicked(translationData: TranslationDetailsData) {
        viewModelScope.launch(Dispatchers.IO) {
            translationsListRepository.updateTranslation(translationData).onFailure {
                _translationListViewAction.emit(TranslationListViewAction.ShowToastError(R.string.error_message_text_failed_to_favorite))
            }
        }
    }

    fun onRemoveButtonClicked(translationData: TranslationDetailsData) {
        viewModelScope.launch(Dispatchers.IO) {
            translationsListRepository.removeTranslation(translationData).onFailure {
                _translationListViewAction.emit(TranslationListViewAction.ShowToastError(R.string.error_message_text_failed_to_remove))
            }
        }
    }

    fun onAddButtonClicked() {
        viewModelScope.launch {
            _translationListViewAction.emit(TranslationListViewAction.NavigateToTranslationScreen)
        }
    }

    private fun observeTranslations() =
        translationsListRepository.observeTranslations()
            .map { translationsList ->
                translationsList.map { translation ->
                    TranslationDetailsData(
                        translationId = translation.id,
                        isFavorite = translation.isFavorite,
                        searchedWord = translation.searchedWord,
                        translatedWord = translation.translatedWord,
                    )
                }
            }.distinctUntilChanged()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            ).catch { e -> Log.e("TranslationsList", "Error in observeTranslations") }

}