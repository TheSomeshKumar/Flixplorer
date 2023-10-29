package com.thesomeshkumar.flixplorer.util

import java.io.Reader
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
val json = Json {
    isLenient = true
    explicitNulls = false
    coerceInputValues = true
    ignoreUnknownKeys = true
}

inline fun <reified I : Any, reified O : Any> parseJson(
    fileName: String,
    crossinline mapFunction: (I) -> O
): O {
    val jsonString = json.jsonStringFromFile(fileName)
    val response = json.decodeFromString<I>(jsonString)
    return mapFunction(response)
}

fun Json.jsonStringFromFile(fileName: String): String {
    val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
    return inputStream
        .bufferedReader()
        .use(Reader::readText)
}
