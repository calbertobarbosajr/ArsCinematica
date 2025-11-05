package com.calberto_barbosa_jr.arscinematica.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class FavoriteMovie(
    @PrimaryKey val id: Int,
    val title: String,
    val posterUrl: String
)