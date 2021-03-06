package com.mortarifabio.marvelcharacterschallenge.database

import androidx.room.*
import com.mortarifabio.marvelcharacterschallenge.model.Favorite

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Query("SELECT * FROM favorites ORDER BY name ASC")
    suspend fun loadFavorites(): List<Favorite>

    @Query("SELECT id FROM favorites")
    suspend fun loadFavoritesIds(): List<Long>

    @Query("SELECT COUNT(id) FROM favorites")
    suspend fun getFavoritesCount(): Int
}