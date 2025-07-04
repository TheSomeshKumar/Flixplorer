package com.thesomeshkumar.flixplorer.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.thesomeshkumar.flixplorer.BuildConfig
import com.thesomeshkumar.flixplorer.core.network.error.NetworkErrorHandler
import com.thesomeshkumar.flixplorer.core.network.interceptors.NetworkConnectionInterceptor
import com.thesomeshkumar.flixplorer.data.datasource.remote.ApiService
import com.thesomeshkumar.flixplorer.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private var json = Json {
        explicitNulls = false
        coerceInputValues = true
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Provides
    @Singleton
    fun provideNetworkErrorHandler(): NetworkErrorHandler = NetworkErrorHandler()

    @Provides
    @Singleton
    fun provideNetworkConnectionInterceptor(
        @ApplicationContext context: Context
    ): NetworkConnectionInterceptor = NetworkConnectionInterceptor(context)

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        networkConnectionInterceptor: NetworkConnectionInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val defaultTimeout = 30L
        return OkHttpClient()
            .newBuilder()
            .callTimeout(defaultTimeout, TimeUnit.SECONDS)
            .connectTimeout(defaultTimeout, TimeUnit.SECONDS)
            .readTimeout(defaultTimeout, TimeUnit.SECONDS)
            .writeTimeout(defaultTimeout, TimeUnit.SECONDS)
            .addInterceptor(networkConnectionInterceptor)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(
                ChuckerInterceptor(context = context).apply {
                    this.redactHeader("api_key")
                }
            )
            .addInterceptor { chain ->
                val original = chain
                    .request()
                    .newBuilder()
                    .build()

                val originalHttpUrl: HttpUrl = original.url

                val url = originalHttpUrl
                    .newBuilder()
                    .addQueryParameter("api_key", BuildConfig.TMDB_KEY)
                    .build()

                val requestBuilder: Request.Builder = original
                    .newBuilder()
                    .url(url)

                val newRequest: Request = requestBuilder.build()
                chain.proceed(newRequest)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit
            .Builder()
            .client(okHttpClient)
            .baseUrl(Constants.TMDB_BASE_URL)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofitClient: Retrofit): ApiService {
        return retrofitClient.create(ApiService::class.java)
    }
}
