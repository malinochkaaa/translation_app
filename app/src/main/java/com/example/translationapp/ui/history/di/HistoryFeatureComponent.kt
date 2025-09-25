package com.example.translationapp.ui.history.di

import android.app.Application
import android.content.Context
import com.example.translationapp.di.TranslationListModule
import com.example.translationapp.ui.history.presentation.HistoryViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TranslationListModule::class])
interface HistoryFeatureComponent {

    fun injectHistoryViewModel(viewModel: HistoryViewModel)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): HistoryFeatureComponent
    }

    companion object {
        private var _instance: HistoryFeatureComponent? = null
        val instance: HistoryFeatureComponent
            get() = _instance!!

        fun init(application: Application) {
            if (_instance == null) {
                _instance = DaggerHistoryFeatureComponent.factory().create(
                    application
                )
            }
        }
    }
}