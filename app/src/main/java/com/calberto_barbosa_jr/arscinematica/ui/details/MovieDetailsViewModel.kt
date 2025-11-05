package com.calberto_barbosa_jr.arscinematica.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calberto_barbosa_jr.arscinematica.data.model.MovieDetails
import com.calberto_barbosa_jr.arscinematica.data.model.VideoResponse
import com.calberto_barbosa_jr.arscinematica.data.remote.TMDbApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class MovieDetailsUiState(
    val loading: Boolean = true,
    val movie: MovieDetails? = null,
    val videoKey: String? = null,
    val error: String? = null
)

class MovieDetailsViewModel(
    private val api: TMDbApi
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailsUiState())
    val uiState: StateFlow<MovieDetailsUiState> = _uiState

    fun loadMovieDetails(movieId: Int, apiKey: String) {
        viewModelScope.launch {
            try {
                _uiState.value = MovieDetailsUiState(loading = true)

                val details = api.getMovieDetails(movieId, apiKey)
                val videos: VideoResponse = api.getMovieVideos(movieId, apiKey)

                val trailer = videos.results.firstOrNull {
                    it.site == "YouTube" && it.type == "Trailer"
                }

                _uiState.value = MovieDetailsUiState(
                    loading = false,
                    movie = details,
                    videoKey = trailer?.key
                )
            } catch (e: Exception) {
                _uiState.value = MovieDetailsUiState(
                    loading = false,
                    error = e.localizedMessage ?: "Erro ao carregar detalhes"
                )
            }
        }
    }
}
