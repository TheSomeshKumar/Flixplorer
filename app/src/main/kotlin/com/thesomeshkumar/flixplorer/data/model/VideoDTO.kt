package com.thesomeshkumar.flixplorer.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class VideoDTO(
    val id: Int,
    @SerializedName("results") val videos: List<Videos>
) {
    @Keep
    data class Videos(
        @SerializedName("iso_639_1") val iso6391: String,
        @SerializedName("iso_3166_1") val iso31661: String,
        val name: String,
        val key: String,
        val site: String,
        val size: Int,
        val type: String,
        val official: Boolean,
        @SerializedName("published_at") val publishedAt: String,
        val id: String
    )
}
