package com.example.translationapp

import android.app.Application
import com.example.translationapp.di.TranslationFeatureComponent

class Application: Application() {

    override fun onCreate() {
        super.onCreate()

        TranslationFeatureComponent.init(application = this)
    }
}