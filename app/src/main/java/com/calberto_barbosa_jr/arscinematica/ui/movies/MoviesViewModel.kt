package com.calberto_barbosa_jr.arscinematica.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.calberto_barbosa_jr.arscinematica.data.model.Movie
import com.calberto_barbosa_jr.arscinematica.data.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class MoviesViewModel(
    private val repository: MoviesRepository
) : ViewModel() {

    val popularMovies: Flow<PagingData<Movie>> =
        repository.getPopularMoviesPager()
            .flow
            .cachedIn(viewModelScope)
}