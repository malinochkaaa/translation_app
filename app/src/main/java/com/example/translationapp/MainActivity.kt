package com.example.translationapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.translationapp.ui.favorites_feature.presentation.FavoritesRoute
import com.example.translationapp.ui.history_feature.presentation.HistoryRoute
import com.example.translationapp.ui.routes.NavRoutes
import com.example.translationapp.ui.translation_feature.presentation.TranslationRoute

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        setContent {
            Main()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Main() {
    val snackbarHostState = remember { SnackbarHostState() }

    val navController = rememberNavController()
    val tabs = listOf(NavRoutes.Translation, NavRoutes.History, NavRoutes.Favorites)
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination

    val resolvedRoute = currentRoute?.route ?: NavRoutes.Translation.route
    val topBarTitle = when (resolvedRoute) {
        NavRoutes.Translation.route -> NavRoutes.Translation.label
        NavRoutes.History.route -> NavRoutes.History.label
        NavRoutes.Favorites.route -> NavRoutes.Favorites.label
        else -> "TranslationApp"
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(topBarTitle, fontSize = 22.sp) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
            )
        },
        bottomBar = {
            NavigationBar {
                tabs.forEach { tab ->
                    val selected = currentRoute
                        ?.hierarchy
                        ?.any { it.route == tab.route } == true
                    NavigationBarItem(
                    selected = selected,
                        onClick = {
                            navController.navigate(tab.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                modifier = Modifier
                                    .width(28.dp)
                                    .height(28.dp),
                                contentDescription = when (tab) {
                                    NavRoutes.Translation -> NavRoutes.Translation.label
                                    NavRoutes.History -> NavRoutes.History.label
                                    NavRoutes.Favorites -> NavRoutes.Favorites.label
                                },
                                imageVector = when (tab) {
                                    NavRoutes.Translation -> Icons.Default.Translate
                                    NavRoutes.History -> Icons.Default.History
                                    NavRoutes.Favorites -> Icons.Default.FavoriteBorder
                                },
                            )
                        },
                        label = {
                            Text(
                                when (tab) {
                                    NavRoutes.Translation -> NavRoutes.Translation.label
                                    NavRoutes.History -> NavRoutes.History.label
                                    NavRoutes.Favorites -> NavRoutes.Favorites.label
                                }
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavRoutes.History.route,
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            composable(NavRoutes.Translation.route) { TranslationRoute(snackbarHostState = snackbarHostState) }
            composable(NavRoutes.History.route) { HistoryRoute(
                snackbarHostState = snackbarHostState,
                navController = navController,
            ) }
            composable(NavRoutes.Favorites.route) { FavoritesRoute(
                snackbarHostState = snackbarHostState,
                navController = navController,
            ) }
        }
    }
}