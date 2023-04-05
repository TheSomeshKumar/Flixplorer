package com.thesomeshkumar.flickophile.data.datasource.local

enum class AppTheme(val string: String) {
    LIGHT("Light Mode"),
    DARK("Dark Mode"),
    FOLLOW_SYSTEM("FOLLOW SYSTEM");

    companion object {
        fun getList(): List<String> {
            return AppTheme.values().map {
                it.string
            }
        }
    }
}
