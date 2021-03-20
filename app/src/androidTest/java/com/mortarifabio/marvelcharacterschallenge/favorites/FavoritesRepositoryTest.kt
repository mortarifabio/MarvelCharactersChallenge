package com.mortarifabio.marvelcharacterschallenge.favorites

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mortarifabio.marvelcharacterschallenge.database.FavoritesDao
import com.mortarifabio.marvelcharacterschallenge.database.MarvelDatabase
import com.mortarifabio.marvelcharacterschallenge.model.Favorite
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class FavoritesRepositoryTest {
    private lateinit var favoritesDao: FavoritesDao
    private lateinit var db: MarvelDatabase.MarvelRoomDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MarvelDatabase.MarvelRoomDatabase::class.java).build()
        favoritesDao = db.favoritesDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun favoriteCrud() = runBlocking {
        val spiderPig = Favorite(27L, null, "Spider-Pig", null, null)
        val ironPig = Favorite(56L, null, "Iron-Pig", null, null)

        favoritesDao.insertFavorite(spiderPig)
        favoritesDao.insertFavorite(ironPig)
        var favorites = favoritesDao.loadFavorites()
        assertEquals(2, favorites.size)

        favoritesDao.insertFavorite(spiderPig)
        favorites = favoritesDao.loadFavorites()
        assertEquals(2, favorites.size)
        assertEquals(ironPig, favorites[0])

        favoritesDao.deleteFavorite(ironPig)
        favorites = favoritesDao.loadFavorites()
        assertEquals(1, favorites.size)
        assertEquals(spiderPig, favorites[0])
    }
}