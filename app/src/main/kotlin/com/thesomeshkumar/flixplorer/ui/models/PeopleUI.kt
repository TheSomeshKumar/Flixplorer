package com.thesomeshkumar.flixplorer.ui.models

import androidx.annotation.Keep

@Keep
data class PeopleUI(
    val id: Int,
    val creditId: String,
    val name: String,
    val role: String,
    val profilePath: String?
)
