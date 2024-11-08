package com.example.tmdb.data.api

import com.example.tmdb.data.models.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface MoviesApi {

    @GET("3/movie/popular")
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiN2VhM2U1NDUxZDQwODU4MjU1ZjMxNjI1ODQ4MTc1NiIsIm5iZiI6MTczMTAwODY2Mi44ODY0MzgsInN1YiI6IjY3MmQxMjA1MjZiNjA1YmMxOWU1ZDY3NCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.qPUd6Z5GJwD0lJkokscsCodPdQiIDVBvBwEb2iHRuAA")
    suspend fun getMovies(): Response<MovieResponse>
}