package com.example.tmdb.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val overview: String,
    val posterPath: String,
    val page: Int
)
