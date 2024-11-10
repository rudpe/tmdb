package com.example.tmdb.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.tmdb.data.api.MoviesApi
import com.example.tmdb.data.dao.MovieDao
import com.example.tmdb.data.dao.MovieFavoriteDao
import com.example.tmdb.data.models.MovieEntity
import com.example.tmdb.data.models.MovieFavoriteEntity
import com.example.tmdb.data.models.MovieResponse
import com.example.core.Resource
import com.example.tmdb.data.AppDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MoviesRepository @Inject constructor(
    private val moviesApi: MoviesApi,
    private val movieDao: MovieDao,
    appDatabase: AppDatabase,
    private val movieFavoriteDao: MovieFavoriteDao
) {

    val moviesPager = Pager(
        config = PagingConfig(pageSize = 20),
        remoteMediator = MovieRemoteMediator(appDatabase, this)
    ) {
        movieDao.pagingSource()
    }

    suspend fun getMovies(page: Int): Resource<MovieResponse> {
        return try {
            val response = moviesApi.getMovies(page)
            if (response.isSuccessful) {
                response.body()?.let { Resource.Success(it) }  ?: Resource.Error(IllegalArgumentException("Body is empty"))
            } else {
                Resource.Error(IllegalArgumentException("Body is empty"))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun updateFavorite(id: Long, isFavorite: Boolean) {
        movieFavoriteDao.upsert(MovieFavoriteEntity(id, isFavorite))
    }

    fun getFavorites(): Flow<List<MovieFavoriteEntity>> = movieFavoriteDao.getFavorites()

    fun geMovieById(id: Long): Flow<MovieEntity> = movieDao.geMovieById(id)

    fun getFavorite(id: Long): Flow<Boolean?> = movieFavoriteDao.getFavorite(id)
}