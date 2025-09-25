package com.example.translationapp.presentation.translation.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TranslationApi {

    @GET("words/search")
    suspend fun getWordTranslation(
        @Query("search") searchWord: String,
    ): Response<TranslationsListResponse>
}