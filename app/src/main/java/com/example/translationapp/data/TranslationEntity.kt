package com.example.translationapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "translations")
data class TranslationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val searchedWord: String,
    val translatedWord: String,
    val isFavorite: Boolean = false,
)