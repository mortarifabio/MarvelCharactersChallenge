package com.mortarifabio.marvelcharacterschallenge.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class CharactersResult(
    val description: String?,
    val id: Long,
    val name: String,
    val thumbnail: Thumbnail?,
    var smallImage: String? = null,
    var largeImage: String? = null,
    var favorite: Boolean = false
) : Parcelable {
    companion object CharactersResultComparator : DiffUtil.ItemCallback<CharactersResult>() {
        override fun areItemsTheSame(oldItem: CharactersResult, newItem: CharactersResult): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: CharactersResult, newItem: CharactersResult): Boolean {
            return oldItem == newItem
        }
    }
}
