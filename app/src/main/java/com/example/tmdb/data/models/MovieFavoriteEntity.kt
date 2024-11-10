package com.example.tmdb.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_favorite")
data class MovieFavoriteEntity(
    @PrimaryKey val id: Long,
    val isFavorite: Boolean
)
