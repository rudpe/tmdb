package com.example.tmdb.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.tmdb.data.api.MoviesApi
import com.example.tmdb.data.dao.MovieDao
import com.example.tmdb.data.models.MovieResponse
import com.example.tmdb.data.models.Resource
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MoviesRepository @Inject constructor(
    private val moviesApi: MoviesApi,
    private val movieDao: MovieDao
) {
    suspend fun getMovies(page: Int): Resource<MovieResponse> {
        try {
            val response = moviesApi.getMovies(page)
            if (response.isSuccessful) {
                return response.body()?.let { Resource.Success(it) }  ?: Resource.Error(IllegalArgumentException("Body is empty"))
            } else {
                return Resource.Error(IllegalArgumentException("Body is empty"))
            }
        } catch (e: Exception) {
            return Resource.Error(e)
        }
    }

    val moviesPager = Pager(
        config = PagingConfig(pageSize = 20),
                remoteMediator = MovieRemoteMediator(movieDao, this)
    ) {
        movieDao.pagingSource()
    }
}