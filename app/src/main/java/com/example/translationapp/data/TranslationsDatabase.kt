package com.example.translationapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [TranslationEntity::class]
)
abstract class TranslationsDatabase : RoomDatabase() {

    abstract fun getTranslationsDao(): TranslationsDao

    companion object {
        @Volatile
        private var INSTANCE: TranslationsDatabase? = null

        fun getTranslationsDatabase(context: Context): TranslationsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TranslationsDatabase::class.java,
                    "translations_database.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}