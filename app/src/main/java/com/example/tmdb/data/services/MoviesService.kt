package com.example.tmdb.data.services

import androidx.paging.PagingData
import com.example.tmdb.data.models.MovieEntity
import com.example.tmdb.data.models.MovieFavoriteEntity
import com.example.tmdb.data.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesService @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    val moviesPager: Flow<PagingData<MovieEntity>> = moviesRepository.moviesPager.flow

    suspend fun updateFavorite(id: Long, isFavorite: Boolean) = moviesRepository.updateFavorite(id, isFavorite)

    fun getFavorites(): Flow<List<MovieFavoriteEntity>> = moviesRepository.getFavorites()

    fun geMovieById(id: Long): Flow<MovieEntity> = moviesRepository.geMovieById(id)

    fun getFavorite(id: Long): Flow<Boolean?> = moviesRepository.getFavorite(id)
}