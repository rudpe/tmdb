package com.example.tmdb.data.models

data class Movie(
    val id: Long,
    val title: String
)

fun Movie.toMovieEntity(page: Int): MovieEntity =
    MovieEntity(id, title, page)