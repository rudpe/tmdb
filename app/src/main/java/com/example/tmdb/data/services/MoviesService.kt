package com.example.tmdb.data.services

import com.example.tmdb.data.api.MoviesApi
import com.example.tmdb.data.repository.MoviesRepository
import javax.inject.Inject

class MoviesService @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend fun getMovies() =
        moviesRepository.getMovies()
}