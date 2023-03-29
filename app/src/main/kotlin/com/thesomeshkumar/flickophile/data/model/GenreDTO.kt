package com.thesomeshkumar.flickophile.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GenreDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
