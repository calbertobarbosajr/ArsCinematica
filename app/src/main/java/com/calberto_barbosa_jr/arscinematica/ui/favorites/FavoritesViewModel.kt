package com.calberto_barbosa_jr.arscinematica.ui.favorites

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import com.calberto_barbosa_jr.arscinematica.data.local.FavoriteMovie
import com.calberto_barbosa_jr.arscinematica.data.local.FavoriteMovieDao
import kotlinx.coroutines.flow.*

class FavoritesViewModel(
    private val dao: FavoriteMovieDao
) : ViewModel() {

    // Lista reativa de todos os favoritos
    val favorites: StateFlow<List<FavoriteMovie>> =
        dao.getAllFavorites()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Estado individual de favorito (para a tela de detalhes)
    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    /**
     * Observa se um filme específico é favorito
     */
    fun observeFavorite(movieId: Int): Flow<Boolean> =
        dao.getAllFavorites()
            .map { favorites -> favorites.any { it.id == movieId } }

    /**
     * Carrega o estado inicial de favorito de um filme
     */
    fun loadFavoriteState(movieId: Int) {
        viewModelScope.launch {
            _isFavorite.value = dao.isFavorite(movieId)
        }
    }

    /**
     * Alterna entre favoritar e desfavoritar
     */
    fun toggleFavorite(id: Int, title: String, posterUrl: String) {
        viewModelScope.launch {
            val isFav = dao.isFavorite(id)
            if (isFav) {
                dao.delete(FavoriteMovie(id, title, posterUrl))
                _isFavorite.value = false
            } else {
                dao.insert(FavoriteMovie(id, title, posterUrl))
                _isFavorite.value = true
            }
        }
    }
}