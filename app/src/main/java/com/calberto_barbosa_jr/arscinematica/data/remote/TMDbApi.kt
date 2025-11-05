package com.calberto_barbosa_jr.arscinematica.data.remote

import com.calberto_barbosa_jr.arscinematica.data.model.MovieDetails
import com.calberto_barbosa_jr.arscinematica.data.model.MovieResponse
import com.calberto_barbosa_jr.arscinematica.data.model.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDbApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "pt-BR",
        @Query("page") page: Int = 1
    ): MovieResponse

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "pt-BR"
    ): VideoResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "pt-BR"
    ): MovieDetails
}