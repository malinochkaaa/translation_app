package com.example.translationapp

import android.app.Application
import com.example.translationapp.ui.favorites.di.FavoritesFeatureComponent
import com.example.translationapp.ui.history.di.HistoryFeatureComponent
import com.example.translationapp.ui.translation.di.TranslationFeatureComponent

class Application: Application() {

    override fun onCreate() {
        super.onCreate()

        TranslationFeatureComponent.init(application = this)
        HistoryFeatureComponent.init(application = this)
        FavoritesFeatureComponent.init(application = this)
    }
}