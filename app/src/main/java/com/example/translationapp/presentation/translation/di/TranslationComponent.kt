package com.example.translationapp.presentation.translation.di

import android.content.Context
import com.example.translationapp.di.RetrofitModule
import com.example.translationapp.presentation.translation.presentation.TranslationViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class])
interface TranslationComponent {

    fun injectTranslationViewModel(viewModel: TranslationViewModel)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): TranslationComponent
    }
}