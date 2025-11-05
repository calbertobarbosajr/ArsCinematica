package com.calberto_barbosa_jr.arscinematica.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.calberto_barbosa_jr.arscinematica.data.model.Movie

class MoviesPagingSource(
    private val api: TMDbApi,
    private val apiKey: String
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val response = api.getPopularMovies(apiKey = apiKey, page = page)
            val movies = response.results

            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (movies.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }
}
