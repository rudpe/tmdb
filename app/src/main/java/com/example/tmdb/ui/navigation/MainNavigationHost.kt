package com.example.tmdb.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tmdb.ui.screens.MoviesScreen
import kotlinx.serialization.Serializable

sealed interface MainRoutes {

    @Serializable
    object Movies : MainRoutes
}

@Composable
fun MainNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = MainRoutes.Movies
    ) {
        composable<MainRoutes.Movies> {
            MoviesScreen()
        }
    }
}