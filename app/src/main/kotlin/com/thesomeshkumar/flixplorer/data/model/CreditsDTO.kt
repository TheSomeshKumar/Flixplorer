package com.thesomeshkumar.flixplorer.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CreditsDTO(
    val cast: List<Cast>,
    val crew: List<Crew>
) {
    @Keep
    data class Cast(
        val adult: Boolean,
        val character: String,
        @SerializedName("credit_id") val creditId: String,
        val gender: Int,
        val id: Int,
        @SerializedName("known_for_department") val knownForDepartment: String,
        val name: String,
        val order: Int,
        @SerializedName("original_name") val originalName: String,
        val popularity: Double,
        @SerializedName("profile_path") val profilePath: String?
    )

    @Keep
    data class Crew(
        val adult: Boolean,
        @SerializedName("credit_id") val creditId: String,
        val department: String,
        val gender: Int,
        val id: Int,
        val job: String,
        @SerializedName("known_for_department") val knownForDepartment: String,
        val name: String,
        @SerializedName("original_name") val originalName: String,
        val popularity: Double,
        @SerializedName("profile_path") val profilePath: String?
    )
}
