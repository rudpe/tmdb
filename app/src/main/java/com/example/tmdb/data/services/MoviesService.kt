package com.example.tmdb.data.services

import com.example.tmdb.data.api.MoviesApi
import com.example.tmdb.data.models.MovieEntity
import com.example.tmdb.data.models.MovieFavoriteEntity
import com.example.tmdb.data.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesService @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    val moviesPager = moviesRepository.moviesPager.flow

    suspend fun getMovies(page: Int) =
        moviesRepository.getMovies(page)

    suspend fun updateFavorite(id: Long, isFavorite: Boolean) = moviesRepository.updateFavorite(id, isFavorite)

    fun getFavoritesFlow() = moviesRepository.getFavoritesFlow()

    fun geMovieById(id: Long): Flow<MovieEntity> = moviesRepository.geMovieById(id)

    fun getFavorite(id: Long): Flow<Boolean?> = moviesRepository.getFavorite(id)
}