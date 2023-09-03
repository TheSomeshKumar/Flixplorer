package com.thesomeshkumar.flixplorer.ui.models

import androidx.annotation.Keep

@Keep
data class CreditUI(
    val id: Int,
    val cast: List<PeopleUI>,
    val crew: List<PeopleUI>
)
