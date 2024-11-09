package com.example.tmdb.data.repository

import android.net.http.HttpException
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.tmdb.data.dao.MovieDao
import com.example.tmdb.data.models.MovieEntity
import com.example.tmdb.data.models.Resource
import com.example.tmdb.data.models.toMovieEntity
import java.io.IOException
import java.net.UnknownHostException
import java.util.logging.Logger

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator (
    //private val page: Int,
    private val movieDao: MovieDao,
    private val moviesRepository: MoviesRepository
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, MovieEntity>): MediatorResult {
        return try {
            // The network load method takes an optional after=<user.id>
            // parameter. For every page after the first, pass the last user
            // ID to let it continue from where it left off. For REFRESH,
            // pass null to load the first page.
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                // In this example, you never need to prepend, since REFRESH
                // will always load the first page in the list. Immediately
                // return, reporting end of pagination.
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()

                    Log.d("juchuu", "APPEND lastitem $lastItem")
                    Log.d("juchuu", "APPEND state $state")
                    // You must explicitly check if the last item is null when
                    // appending, since passing null to networkService is only
                    // valid for initial load. If lastItem is null it means no
                    // items were loaded after the initial REFRESH and there are
                    // no more items to load.
                    if (lastItem == null && movieDao.getMaxPage() != 1) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }
                    Log.d("juchuu", "APPEND lastitem.page ${lastItem?.page}")
                    (lastItem?.page ?: 0) + 1
                }
            }

            // Suspending network load via Retrofit. This doesn't need to be
            // wrapped in a withContext(Dispatcher.IO) { ... } block since
            // Retrofit's Coroutine CallAdapter dispatches on a worker
            // thread.
            val response = moviesRepository.getMovies(loadKey ?: 1) ?: return MediatorResult.Error(IllegalArgumentException())

            when (response) {
                is Resource.Error -> {
                    MediatorResult.Error(response.exception)
                }
                is Resource.Success -> {
                    //database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        movieDao.deleteByQuery(loadKey ?: 1)
                    }

                    // Insert new users into database, which invalidates the
                    // current PagingData, allowing Paging to present the updates
                    // in the DB.
                    movieDao.insertAll(response.data.results.map { it.toMovieEntity(loadKey ?: 1) })
                    //}

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