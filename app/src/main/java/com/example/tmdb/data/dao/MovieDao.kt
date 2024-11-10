package com.example.tmdb.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tmdb.data.models.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<MovieEntity>)

    @Query("SELECT * FROM movies ORDER BY page")
    fun pagingSource(): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM movies WHERE id=:id")
    fun geMovieById(id: Long): Flow<MovieEntity>

    @Query("DELETE FROM movies WHERE page = :page")
    suspend fun deleteByPage(page: Int)

    @Query("SELECT MAX(page) FROM movies")
    suspend fun getMaxPage(): Int

    @Query("DELETE FROM movies")
    suspend fun clearAll()
}