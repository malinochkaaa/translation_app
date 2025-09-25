package com.example.translationapp.ui

class TranslationService {

    private var translationsList = mutableListOf<TranslationDetailsData>()

    init {
        translationsList = (1..15).map {
            TranslationDetailsData(
                translationId = it.toLong(),
                isFavorite = it in 5..7,
                searchedWord = "hello",
                translatedWord = "привет"
            )
        }.toMutableList()
    }

    fun likePerson(translations: TranslationDetailsData) {
        val index = translationsList.indexOfFirst { it.translationId == translations.translationId } // Находим индекс человека в списке
        if (index == -1) return // Останавливаемся, если не находим такого человека

        translationsList = ArrayList(translationsList) // Создаем новый список
        translationsList[index] = translationsList[index].copy(isFavorite = !translationsList[index].isFavorite) // Меняем значение "лайка" на противоположное
    }

    fun removePerson(translations: TranslationDetailsData) {
        val index = translationsList.indexOfFirst { it.translationId == translations.translationId } // Находим индекс человека в списке
        if (index == -1) return // Останавливаемся, если не находим такого человека

        translationsList = ArrayList(translationsList) // Создаем новый список
        translationsList.removeAt(index) // Удаляем человека
    }
}