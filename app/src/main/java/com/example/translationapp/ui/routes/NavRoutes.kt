package com.example.translationapp.ui.routes

sealed class NavRoutes(val route: String, val label: String) {
    object Favorites : NavRoutes("favorites", "Favorites")
    object History : NavRoutes("history", "History")
    object Translation : NavRoutes("translation", "Translation")
}