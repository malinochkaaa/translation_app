package com.example.translationapp.ui.translation_feature.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TranslationsListResponse(
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