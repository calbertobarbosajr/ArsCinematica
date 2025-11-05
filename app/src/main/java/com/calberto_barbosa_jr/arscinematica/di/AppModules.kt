package com.calberto_barbosa_jr.arscinematica.di

import androidx.room.Room
import com.calberto_barbosa_jr.arscinematica.data.local.AppDatabase
import com.calberto_barbosa_jr.arscinematica.BuildConfig
import com.calberto_barbosa_jr.arscinematica.ui.favorites.FavoritesViewModel
import com.calberto_barbosa_jr.arscinematica.data.remote.TMDbApi
import com.calberto_barbosa_jr.arscinematica.data.repository.MoviesRepository
import com.calberto_barbosa_jr.arscinematica.ui.details.MovieDetailsViewModel
import com.calberto_barbosa_jr.arscinematica.ui.movies.MoviesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TMDbApi::class.java)
    }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "movies_db"
        ).build()
    }
    single { get<AppDatabase>().favoriteMovieDao() }
}

val repositoryModule = module {
    single { MoviesRepository(get(), BuildConfig.TMDB_API_KEY) }
}

val viewModelModule = module {
    viewModel { MoviesViewModel(get()) }
    viewModel { FavoritesViewModel(get()) }
    viewModel { MovieDetailsViewModel(get()) }
}
