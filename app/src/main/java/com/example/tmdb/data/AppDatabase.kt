package com.example.tmdb.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tmdb.data.dao.MovieDao
import com.example.tmdb.data.models.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}