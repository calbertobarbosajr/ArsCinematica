package com.calberto_barbosa_jr.arscinematica.data.model

data class Movie(
    val id: Int,
    val title: String,
    val poster_path: String?
) {
    fun getPosterUrl(): String {
        return "https://image.tmdb.org/t/p/w500${poster_path}"
    }
}