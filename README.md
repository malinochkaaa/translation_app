# Android-приложение для перевода слов с возможностью сохранения истории переводов и добавления их в избранное
Приложение на Jetpack Compose, которое использует [SkyengApi](https://dictionary.skyeng.ru/doc/api/external) для получения переводов и состоит из: экрана перевода, экрана истории переводов и экрана избранного.
Также поддержано кэширование переводов и офлайн-режим для ранее найденных слов. Приложение построено по принципам Clean Architecture.

## Стек технологий:
**Архитектура:** MVVM, Clean Architecture, Single Activity, Compose Navigation  
**Асинхронность:** Kotlin Coroutines, Flow, StateFlow  
**Сеть:** Retrofit, OkHttp  
**Хранение данных:** Room  
**DI:** Dagger  
**UI:** Jetpack Compose, Material 3, Snackbar, Floating Action Button

## Демонстрация работы
<p align="center">
  <img src="./docs/demo_recording.gif" width="320" alt="Демонстрация работы приложения" />
</p>

## Скриншоты
<p align="center">
  <img src="./docs/screenshots/translation_screenshot.png" width="230" alt="Экран перевода слов" />
  <img src="./docs/screenshots/history_screenshot.png" width="230" alt="Экран истории переводов" />
  <img src="./docs/screenshots/favorites_screenshot.png" width="230" alt="Экран избранного" />
</p>

## Автор
Алина Акимова  
Android-разработчик  
[GitHub](https://github.com/malinochkaaa)