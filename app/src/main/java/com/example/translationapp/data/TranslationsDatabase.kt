package com.example.translationapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [TranslationEntity::class]
)
abstract class TranslationsDatabase : RoomDatabase() {

    abstract fun getTranslationsDao(): TranslationsDao
}