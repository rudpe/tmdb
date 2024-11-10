package com.example.tmdb.data.models

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Long,
    val title: String?,
    val overview: String?,
    @SerializedName("backdrop_path") val posterPath: String?
)

fun Movie.toMovieEntity(page: Int): MovieEntity =
    MovieEntity(id, title.orEmpty(), overview.orEmpty(), posterPath.orEmpty(), page)