package com.example.tmdb.data.api

import com.example.tmdb.data.models.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface MoviesApi {

    @GET("3/movie/popular")
    suspend fun getMovies(
        @Query("page") page: Int
    ): Response<MovieResponse>
}