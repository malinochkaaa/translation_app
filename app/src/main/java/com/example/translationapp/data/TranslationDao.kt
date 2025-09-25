package com.example.translationapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TranslationsDao {
    @Query("SELECT * FROM translations WHERE isFavorite = 1")
    fun observeFavorites(): Flow<List<TranslationEntity>>

    @Query("SELECT * FROM translations")
    fun observeTranslations(): Flow<List<TranslationEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTranslation(entity: TranslationEntity): Long

    @Update
    fun updateTranslation(entity: TranslationEntity)

    @Delete
    fun removeTranslation(entity: TranslationEntity)
}