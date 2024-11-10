package com.example.tmdb.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.core.Resource
import com.example.tmdb.data.AppDatabase
import com.example.tmdb.data.models.MovieEntity
import com.example.tmdb.data.models.toMovieEntity

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator (
    private val appDatabase: AppDatabase,
    private val moviesRepository: MoviesRepository
) : RemoteMediator<Int, MovieEntity>() {

    private val movieDao = appDatabase.movieDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, MovieEntity>): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()


                    if (lastItem == null && movieDao.getMaxPage() != 1) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }
                    (lastItem?.page ?: 0) + 1
                }
            }

            val response = moviesRepository.getMovies(loadKey ?: 1) ?: return MediatorResult.Error(IllegalArgumentException())

            when (response) {
                is Resource.Error -> {
                    MediatorResult.Error(response.exception)
                }
                is Resource.Success -> {
                    appDatabase.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            movieDao.deleteByPage(loadKey ?: 1)
                        }

                        movieDao.insertAll(response.data.results.map { it.toMovieEntity(loadKey ?: 1) })
                    }

                    MediatorResult.Success(
                        endOfPaginationReached = response.data.totalPages == loadKey
                    )
                }
            }


        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}