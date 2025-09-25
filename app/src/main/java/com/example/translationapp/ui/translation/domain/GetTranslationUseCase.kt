package com.example.translationapp.ui.translation.domain

import com.example.translationapp.ui.translation.data.TranslationApi

class GetTranslationUseCase(
    private val translationApi: TranslationApi,
) {
    suspend fun getWordTranslationData(searchWord: String): String? {
        val response = translationApi.getWordTranslation(searchWord)

        return if (response.isSuccessful) {
            val translationResponse = response.body()
            translationResponse?.let {
                it.firstOrNull()?.meanings?.firstOrNull()?.translation?.text
            }
        } else {
            null
        }
    }
}