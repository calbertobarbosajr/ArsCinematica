package com.calberto_barbosa_jr.arscinematica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.calberto_barbosa_jr.arscinematica.ui.theme.ArsCinematicaTheme
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.calberto_barbosa_jr.arscinematica.ui.details.MovieDetailsScreen
import com.calberto_barbosa_jr.arscinematica.ui.favorites.FavoritesScreen
import com.calberto_barbosa_jr.arscinematica.ui.movies.MovieListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val apiKey = BuildConfig.TMDB_API_KEY

        setContent {
            val navController = rememberNavController()
            ArsCinematicaTheme {
                NavHost(navController = navController, startDestination = "list") {
                    composable("list") {
                        MovieListScreen(navController)
                    }
                    composable("details/{movieId}") { backStackEntry ->
                        val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull() ?: 0
                        MovieDetailsScreen(movieId, apiKey)
                    }
                    composable("favorites") {
                        FavoritesScreen(navToDetails = { id -> navController.navigate("details/$id") })
                    }
                }
            }
        }
    }
}