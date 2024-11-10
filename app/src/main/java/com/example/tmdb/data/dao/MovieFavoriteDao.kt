package com.example.tmdb.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.tmdb.data.models.MovieFavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieFavoriteDao {

    @Query("SELECT * FROM movies_favorite WHERE isFavorite=1")
    fun getFavorites(): Flow<List<MovieFavoriteEntity>>

    @Query("SELECT isFavorite FROM movies_favorite WHERE id=:id")
    fun getFavorite(id: Long): Flow<Boolean?>

    @Upsert
    suspend fun upsert(favorite: MovieFavoriteEntity)
}