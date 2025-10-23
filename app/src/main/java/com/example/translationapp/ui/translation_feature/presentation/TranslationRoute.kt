package com.example.translationapp.ui.translation_feature.presentation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TranslationRoute(
    viewModel: TranslationViewModel = viewModel(),
    snackbarHostState: SnackbarHostState,
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val viewState by viewModel.translationViewState.collectAsStateWithLifecycle(
        initialValue = null,
        lifecycle = lifecycle,
    )
    val errorMessage = viewState?.errorMessage
    var query by remember { mutableStateOf("") }

    val context = LocalContext.current
    LaunchedEffect(errorMessage) {
        errorMessage?.let { msg ->
            snackbarHostState.showSnackbar(context.getString(msg))
        }
    }

    TranslationScreen(
        outlinedText = query,
        text = viewState?.foundWordText.orEmpty(),
        onValueChange = { query = it },
        onTranslateButtonClick = { viewModel.onButtonClicked(query) }
    )
}