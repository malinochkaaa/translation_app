package com.example.translationapp.ui.translation.domain

import android.util.Log
import com.example.translationapp.ui.translation.data.TranslationApi

class GetTranslationUseCase(
    private val translationApi: TranslationApi,
) {
    suspend fun getWordTranslationData(searchWord: String): String? {
        return try {
            val response = translationApi.getWordTranslation(searchWord)

            if (response.isSuccessful) {
                val translationResponse = response.body()
                translationResponse?.let {
                    it.firstOrNull()?.meanings?.firstOrNull()?.translation?.text
                }
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("GetTranslationUseCase", "Error in getWordTranslationData $e")
            null
        }
    }
}