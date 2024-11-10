package com.example.tmdb.data.di

import android.content.Context
import androidx.room.Room
import com.example.tmdb.BuildConfig
import com.example.tmdb.data.AppDatabase
import com.example.tmdb.data.api.MoviesApi
import com.example.tmdb.data.dao.MovieDao
import com.example.tmdb.data.dao.MovieFavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    @Provides
    @Singleton
    fun provideDefaultOkHttpClient(
        @ApplicationContext context: Context,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            //.addInterceptor(headersInterceptor)


            .addInterceptor(httpLoggingInterceptor)

            .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit): MoviesApi = retrofit.create(MoviesApi::class.java)

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase =
        Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "movies_database"
        ).build()

    @Provides
    @Singleton
    fun provideMovieDao(appDatabase: AppDatabase) : MovieDao = appDatabase.movieDao()

    @Provides
    @Singleton
    fun provideMovieFavoriteDao(appDatabase: AppDatabase) : MovieFavoriteDao = appDatabase.movieFavoriteDao()

}