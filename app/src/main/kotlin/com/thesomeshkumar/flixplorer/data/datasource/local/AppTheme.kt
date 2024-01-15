package com.thesomeshkumar.flixplorer.data.datasource.local

enum class AppTheme(val string: String) {
    LIGHT("Light Mode"), DARK("Dark Mode"), SYSTEM_DEFAULT("System Default");

    companion object {
        fun getList(): List<String> {
            return entries
                .map {
                    it.string
                }
        }
    }
}
