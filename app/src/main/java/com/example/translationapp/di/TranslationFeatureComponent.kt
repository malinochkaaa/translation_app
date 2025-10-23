package com.example.translationapp.di

import android.app.Application
import android.content.Context
import com.example.translationapp.ui.favorites_feature.presentation.FavoritesViewModel
import com.example.translationapp.ui.history_feature.presentation.HistoryViewModel
import com.example.translationapp.ui.translation_feature.presentation.TranslationViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    RetrofitModule::class,
    SkyengApiModule::class,
    GetTranslationModule::class,
    TranslationListModule::class,
    DatabaseModule::class,
])
interface TranslationFeatureComponent {

    fun injectTranslationViewModel(viewModel: TranslationViewModel)

    fun injectHistoryViewModel(viewModel: HistoryViewModel)

    fun injectFavoritesViewModel(viewModel: FavoritesViewModel)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): TranslationFeatureComponent
    }

    companion object {
        private var _instance: TranslationFeatureComponent? = null
        val instance: TranslationFeatureComponent
            get() = _instance!!

        fun init(application: Application) {
            if (_instance == null) {
                _instance = DaggerTranslationFeatureComponent.factory().create(
                    application
                )
            }
        }
    }
}