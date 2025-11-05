package com.calberto_barbosa_jr.arscinematica.ui.details

import android.content.Intent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.calberto_barbosa_jr.arscinematica.components.YouTubeTrailerPlayer
import com.calberto_barbosa_jr.arscinematica.ui.favorites.FavoritesViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieDetailsScreen(
    movieId: Int,
    apiKey: String,
    favoritesViewModel: FavoritesViewModel = koinViewModel(),
    detailsViewModel: MovieDetailsViewModel = koinViewModel()
) {
    val uiState by detailsViewModel.uiState.collectAsState()
    val isFavorite by favoritesViewModel.observeFavorite(movieId).collectAsState(initial = false)
    val context = LocalContext.current

    // Carrega os detalhes ao entrar
    LaunchedEffect(movieId) {
        detailsViewModel.loadMovieDetails(movieId, apiKey)
        favoritesViewModel.loadFavoriteState(movieId)
    }

    when {
        uiState.loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        uiState.error != null -> {
            Text("Erro: ${uiState.error}", Modifier.padding(16.dp))
        }

        uiState.movie != null -> {
            val movie = uiState.movie!!
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(movie.title, style = MaterialTheme.typography.headlineSmall)
                    Row {
                        FavoriteButton(
                            isFavorite = isFavorite,
                            onClick = {
                                favoritesViewModel.toggleFavorite(
                                    movie.id,
                                    movie.title,
                                    movie.getPosterUrl()
                                )
                            }
                        )

                        // 🔗 Botão de compartilhar corrigido
                        IconButton(onClick = {
                            uiState.videoKey?.let { key ->
                                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(
                                        Intent.EXTRA_TEXT,
                                        "Confira o trailer de ${movie.title}: https://www.youtube.com/watch?v=$key"
                                    )
                                }
                                context.startActivity(
                                    Intent.createChooser(
                                        shareIntent,
                                        "Compartilhar trailer via"
                                    )
                                )
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Compartilhar"
                            )
                        }
                    }
                }

                Spacer(Modifier.height(8.dp))

                Image(
                    painter = rememberAsyncImagePainter(movie.getPosterUrl()),
                    contentDescription = movie.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                )

                Spacer(Modifier.height(8.dp))
                Text(
                    "Data de lançamento: ${movie.release_date}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    "Avaliação: ${String.format("%.1f", movie.vote_average)} ★",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(Modifier.height(8.dp))
                Text("Sinopse", style = MaterialTheme.typography.titleMedium)
                Text(
                    movie.overview.ifEmpty { "Sinopse não disponível" },
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(Modifier.height(16.dp))
                uiState.videoKey?.let {
                    Text("Trailer", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    YouTubeTrailerPlayer(videoKey = it, modifier = Modifier.fillMaxWidth())
                } ?: Text("Trailer não encontrado")
            }
        }

        else -> {
            Text("Filme não encontrado", Modifier.padding(16.dp))
        }
    }
}

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onClick: () -> Unit
) {
    val animatedColor by animateColorAsState(
        targetValue = if (isFavorite) Color.Red else Color.Gray,
        animationSpec = tween(durationMillis = 400)
    )

    val scale by animateFloatAsState(
        targetValue = if (isFavorite) 1.3f else 1f,
        animationSpec = keyframes {
            durationMillis = 300
            1.3f at 100 with LinearOutSlowInEasing
            1f at 300
        }
    )

    IconButton(onClick = onClick, modifier = Modifier.scale(scale)) {
        Icon(
            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = if (isFavorite) "Remover dos favoritos" else "Adicionar aos favoritos",
            tint = animatedColor,
            modifier = Modifier.size(28.dp)
        )
    }
}