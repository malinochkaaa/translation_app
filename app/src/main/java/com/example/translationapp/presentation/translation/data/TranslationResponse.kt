package com.example.translationapp.presentation.translation.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TranslationsListResponse(
    val searchData: List<SearchData>,
)

@JsonClass(generateAdapter = true)
data class SearchData(
    val meanings: List<MeaningData>,
)

@JsonClass(generateAdapter = true)
data class MeaningData(
    val translation: TranslationData,
)

@JsonClass(generateAdapter = true)
data class TranslationData(
    val text: String,
)