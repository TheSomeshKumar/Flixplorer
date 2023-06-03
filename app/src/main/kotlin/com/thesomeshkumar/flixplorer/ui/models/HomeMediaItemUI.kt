package com.thesomeshkumar.flixplorer.ui.models

import androidx.annotation.Keep

@Keep
data class HomeMediaItemUI(
    val id: Int,
    val name: String,
    val posterPath: String,
    val backdropPath: String,
    val overview: String
)
