package com.thesomeshkumar.flixplorer.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreditsDTO(
    val cast: List<Cast>,
    val crew: List<Crew>
) {
    @Serializable
    data class Cast(
        val adult: Boolean,
        val character: String,
        @SerialName("credit_id") val creditId: String,
        val gender: Int,
        val id: Int,
        @SerialName("known_for_department") val knownForDepartment: String,
        val name: String,
        val order: Int,
        @SerialName("original_name") val originalName: String,
        val popularity: Double,
        @SerialName("profile_path") val profilePath: String?
    )

    @Serializable
    data class Crew(
        val adult: Boolean,
        @SerialName("credit_id") val creditId: String,
        val department: String,
        val gender: Int,
        val id: Int,
        val job: String,
        @SerialName("known_for_department") val knownForDepartment: String,
        val name: String,
        @SerialName("original_name") val originalName: String,
        val popularity: Double,
        @SerialName("profile_path") val profilePath: String?
    )
}
