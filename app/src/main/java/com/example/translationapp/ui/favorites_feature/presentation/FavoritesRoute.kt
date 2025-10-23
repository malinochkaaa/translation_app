package com.example.translationapp.ui.favorites_feature.presentation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.translationapp.R
import com.example.translationapp.ui.actions.TranslationListViewAction
import com.example.translationapp.ui.routes.NavRoutes
import com.example.translationapp.ui.screens.TranslationsListScreen

@Composable
fun FavoritesRoute (
    viewModel: FavoritesViewModel = viewModel(),
    snackbarHostState: SnackbarHostState,
    navController: NavController,
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val viewState by viewModel.favoritesData.collectAsStateWithLifecycle(
        initialValue = null,
        lifecycle = lifecycle,
    )
    val action by viewModel.translationListViewAction.collectAsStateWithLifecycle(
        initialValue = null)
    val context = LocalContext.current

    when (action) {
        TranslationListViewAction.NavigateToTranslationScreen -> navController.navigate(NavRoutes.Translation.route) {
            launchSingleTop = true
            restoreState = true
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
        }
        is TranslationListViewAction.ShowToastError -> {
            val errorMessage = (action as TranslationListViewAction.ShowToastError).errorMessage
            LaunchedEffect(errorMessage) {
                snackbarHostState.showSnackbar(context.getString(errorMessage))
            }
        }
        null -> null
    }

    TranslationsListScreen(
        emptyListText = context.getString(R.string.favorites_fragment_empty_list_text),
        items = viewState,
        onAddClick = { viewModel.onAddButtonClicked() },
        onLikeButtonClick = { item -> viewModel.onFavoriteButtonClicked(item) },
        onRemoveButtonClick = { item -> viewModel.onRemoveButtonClicked(item) },
    )
}