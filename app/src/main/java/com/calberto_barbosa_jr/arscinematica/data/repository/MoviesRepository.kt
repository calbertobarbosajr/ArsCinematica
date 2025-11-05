package com.calberto_barbosa_jr.arscinematica.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.calberto_barbosa_jr.arscinematica.data.model.Movie
import com.calberto_barbosa_jr.arscinematica.data.remote.MoviesPagingSource
import com.calberto_barbosa_jr.arscinematica.data.remote.TMDbApi

class MoviesRepository(private val api: TMDbApi, private val apiKey: String) {
    fun getPopularMoviesPager(): Pager<Int, Movie> {
        return Pager(
            config = PagingConfig(
                pageSize = 20, // TMDb retorna 20 por página
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviesPagingSource(api, apiKey) }
        )
    }
}