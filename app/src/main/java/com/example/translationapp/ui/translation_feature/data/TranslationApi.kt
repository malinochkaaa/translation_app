package com.example.translationapp.ui.translation_feature.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TranslationApi {

    @GET("words/search")
    suspend fun getWordTranslation(
        @Query("search") searchWord: String,
    ): Response<List<TranslationsListResponse>>
}