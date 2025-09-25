package com.example.translationapp.ui.favorites.di

import android.content.Context
import com.example.translationapp.Application
import com.example.translationapp.di.TranslationListModule
import com.example.translationapp.ui.history.presentation.HistoryViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TranslationListModule::class])
interface FavoritesFeatureComponent {

    fun injectHistoryViewModel(viewModel: HistoryViewModel)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): FavoritesFeatureComponent
    }

    companion object {
        private var _instance: FavoritesFeatureComponent? = null
        val instance: FavoritesFeatureComponent
            get() = _instance!!

        fun init(application: Application) {
            if (_instance == null) {
                _instance = DaggerFavoritesFeatureComponent.factory().create(
                    application
                )
            }
        }
    }
}