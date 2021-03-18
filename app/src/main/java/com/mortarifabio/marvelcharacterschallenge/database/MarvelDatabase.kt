package com.mortarifabio.marvelcharacterschallenge.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mortarifabio.marvelcharacterschallenge.model.Favorite
import com.mortarifabio.marvelcharacterschallenge.utils.Constants.Database.DB_NAME

object MarvelDatabase {

    @Database(
        entities = [
            Favorite::class
        ],
        version = 1,
        exportSchema = false
    )

    abstract class MarvelRoomDatabase : RoomDatabase() {
        abstract fun favoritesDao(): FavoritesDao
    }

    fun getDatabase(context: Context): MarvelRoomDatabase {
        return Room.databaseBuilder(
            context,
            MarvelRoomDatabase::class.java,
            DB_NAME
        ).build()
    }
}