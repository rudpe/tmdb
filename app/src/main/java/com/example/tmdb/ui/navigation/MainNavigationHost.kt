package com.example.tmdb.ui.navigation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tmdb.data.models.AppTheme
import com.example.tmdb.ui.screens.DetailScreen
import com.example.tmdb.ui.screens.InfoScreen
import com.example.tmdb.ui.screens.MoviesScreen
import com.example.tmdb.ui.screens.SettingsScreen
import com.example.tmdb.ui.theme.TmdbTheme
import com.example.tmdb.ui.viewmodels.ThemeViewModel
import kotlinx.serialization.Serializable

sealed interface MainRoutes {

    @Serializable
    object Movies : MainRoutes

    @Serializable
    data class Detail(val movieId: Long) : MainRoutes

    @Serializable
    object Info : MainRoutes

    @Serializable
    object Settings : MainRoutes
}

@Composable
fun MainNavHost(navController: NavHostController = rememberNavController()) {
    val themeViewModel: ThemeViewModel = hiltViewModel()
    val theme by themeViewModel.theme.collectAsStateWithLifecycle()
    val darkTheme = when (theme) {
        AppTheme.SYSTEM -> isSystemInDarkTheme()
        AppTheme.LIGHT -> false
        AppTheme.DARK -> true
    }
    TmdbTheme(darkTheme = darkTheme) {
        NavHost(
            navController = navController,
            startDestination = MainRoutes.Movies
        ) {
            composable<MainRoutes.Movies> {
                MoviesScreen(
                    onMovieClick = { navController.navigate(MainRoutes.Detail(it)) },
                    onInfoClick = { navController.navigate(MainRoutes.Info) },
                    onSettingsClick = { navController.navigate(MainRoutes.Settings) }
                )
            }
            composable<MainRoutes.Detail> {
                DetailScreen({ navController.popBackStack() })
            }
            composable<MainRoutes.Info> {
                InfoScreen { navController.popBackStack() }
            }
            composable<MainRoutes.Settings> {
                SettingsScreen({ navController.popBackStack() }, { themeViewModel.updateTheme(it) })
            }
        }
    }
}