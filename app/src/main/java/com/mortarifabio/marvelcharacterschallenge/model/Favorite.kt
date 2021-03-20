package com.mortarifabio.marvelcharacterschallenge.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mortarifabio.marvelcharacterschallenge.utils.Constants.Database.DB_TABLE_FAVORITES
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = DB_TABLE_FAVORITES)
data class Favorite(
    @PrimaryKey
    val id: Long,
    val description: String?,
    val name: String,
    var smallImage: String? = null,
    var largeImage: String? = null
) : Parcelable