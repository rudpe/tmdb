package com.example.tmdb.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tmdb.data.dao.MovieDao
import com.example.tmdb.data.dao.MovieFavoriteDao
import com.example.tmdb.data.models.MovieEntity
import com.example.tmdb.data.models.MovieFavoriteEntity

@Database(entities = [MovieEntity::class, MovieFavoriteEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun movieFavoriteDao(): MovieFavoriteDao
}