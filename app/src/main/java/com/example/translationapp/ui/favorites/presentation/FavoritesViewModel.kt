package com.example.translationapp.ui.favorites.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.translationapp.di.TranslationFeatureComponent
import com.example.translationapp.domain.TranslationsListRepository
import com.example.translationapp.ui.TranslationDetailsData
import com.example.translationapp.ui.TranslationListViewAction
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

class FavoritesViewModel : ViewModel() {

    @Inject
    lateinit var translationsListRepository: TranslationsListRepository
    var favoritesData: Flow<List<TranslationDetailsData>>

    private val _translationListViewAction = MutableSharedFlow<TranslationListViewAction>()
    val translationListViewAction: SharedFlow<TranslationListViewAction> = _translationListViewAction.asSharedFlow()

    init {
        TranslationFeatureComponent.instance.injectFavoritesViewModel(this)
        favoritesData = observeFavorites()
    }

    fun onFavoriteButtonClicked(translationData: TranslationDetailsData) {
        viewModelScope.launch(Dispatchers.IO) {
            translationsListRepository.updateTranslation(translationData)
        }
    }

    fun onRemoveButtonClicked(translationData: TranslationDetailsData) {
        viewModelScope.launch(Dispatchers.IO) {
            translationsListRepository.removeTranslation(translationData)
        }
    }

    fun onAddButtonClicked() {
        viewModelScope.launch {
            _translationListViewAction.emit(TranslationListViewAction.NavigateToTranslationScreen)
        }
    }

    private fun observeFavorites() =
        translationsListRepository.observeFavorites()
            .map { favoritesList ->
                favoritesList.map { favorite ->
                    TranslationDetailsData(
                        translationId = favorite.id,
                        isFavorite = true,
                        searchedWord = favorite.searchedWord,
                        translatedWord = favorite.translatedWord,
                    )
                }
            }.distinctUntilChanged()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            ).catch { e -> Log.e("Lol", "Error in observeFavorites") }
}