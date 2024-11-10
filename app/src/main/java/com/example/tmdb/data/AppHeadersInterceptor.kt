package com.example.tmdb.data

import okhttp3.Interceptor
import okhttp3.Response

class AppHeadersInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiN2VhM2U1NDUxZDQwODU4MjU1ZjMxNjI1ODQ4MTc1NiIsIm5iZiI6MTczMTAwODY2Mi44ODY0MzgsInN1YiI6IjY3MmQxMjA1MjZiNjA1YmMxOWU1ZDY3NCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.qPUd6Z5GJwD0lJkokscsCodPdQiIDVBvBwEb2iHRuAA")
        return chain.proceed(request.build())
    }
}