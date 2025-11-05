package com.calberto_barbosa_jr.arscinematica.ui.movies

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieListScreen(navController: NavController, viewModel: MoviesViewModel = koinViewModel()) {
    val movies = viewModel.popularMovies.collectAsLazyPagingItems()

    LazyColumn {
        items(movies.itemCount) { index ->
            movies[index]?.let { movie ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { navController.navigate("details/${movie.id}") }
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(movie.getPosterUrl()),
                        contentDescription = movie.title,
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(movie.title)
                }
            }
        }
    }
}