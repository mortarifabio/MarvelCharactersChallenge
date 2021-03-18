package com.mortarifabio.marvelcharacterschallenge.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
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
) : Parcelable {
    companion object {
        var DIFF_CALLBACK: DiffUtil.ItemCallback<Favorite> =
            object : DiffUtil.ItemCallback<Favorite>() {
                override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                    return oldItem == newItem
                }
            }
    }
}