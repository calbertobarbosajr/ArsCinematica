package com.calberto_barbosa_jr.arscinematica.data.model

data class MovieResponse(
    val page: Int,
    val results: List<Movie>
)