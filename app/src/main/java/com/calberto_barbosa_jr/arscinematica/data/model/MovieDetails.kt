package com.calberto_barbosa_jr.arscinematica.data.model

data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String?,
    val vote_average: Float,
    val release_date: String
) {
    fun getPosterUrl(): String {
        return "https://image.tmdb.org/t/p/w500${poster_path}"
    }
}