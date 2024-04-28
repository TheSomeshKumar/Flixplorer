package com.thesomeshkumar.flixplorer.di

import android.app.Application
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.thesomeshkumar.flixplorer.BuildConfig
import com.thesomeshkumar.flixplorer.data.datasource.local.UserPreferences
import com.thesomeshkumar.flixplorer.data.datasource.remote.ApiService
import com.thesomeshkumar.flixplorer.data.datasource.remote.RemoteDataSourceImpl
import com.thesomeshkumar.flixplorer.data.repository.FlixplorerRepository
import com.thesomeshkumar.flixplorer.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
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
object AppModule {

    @OptIn(ExperimentalSerializationApi::class)
    private var json = Json {
        explicitNulls = false
        coerceInputValues = true
        ignoreUnknownKeys = true
    }

    @Singleton
    @Provides
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

    @Singleton
    @Provides
    fun provideOkhttpClient(loggingInterceptor: HttpLoggingInterceptor, application: Application): OkHttpClient {
        val defaultTimeout = 30L
        return OkHttpClient()
            .newBuilder()
            .callTimeout(defaultTimeout, TimeUnit.SECONDS)
            .connectTimeout(defaultTimeout, TimeUnit.SECONDS)
            .readTimeout(defaultTimeout, TimeUnit.SECONDS)
            .writeTimeout(defaultTimeout, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(
                ChuckerInterceptor(context = application.applicationContext).apply {
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

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    @Singleton
    @Provides
    fun provideApiServices(retrofitClient: Retrofit): ApiService {
        return retrofitClient.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideFlixplorerRepository(
        api: ApiService,
        userPreferences: UserPreferences
    ): FlixplorerRepository {
        val remoteDataSourceImpl = RemoteDataSourceImpl(api)
        return FlixplorerRepository(remoteDataSourceImpl, userPreferences)
    }

    @Provides
    @Singleton
    fun providesDataStore(application: Application): UserPreferences =
        UserPreferences(application.applicationContext)
}
