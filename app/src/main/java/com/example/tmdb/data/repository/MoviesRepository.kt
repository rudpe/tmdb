package com.example.tmdb.data.repository

import com.example.tmdb.data.api.MoviesApi
import com.example.tmdb.data.dao.MovieDao
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val moviesApi: MoviesApi,
    private val movieDao: MovieDao
) {
    suspend fun getMovies() =
        moviesApi.getMovies()
}