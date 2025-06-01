package com.thesomeshkumar.flixplorer.di

import android.app.Application
import com.thesomeshkumar.flixplorer.data.datasource.local.UserPreferences
import com.thesomeshkumar.flixplorer.data.datasource.remote.ApiService
import com.thesomeshkumar.flixplorer.data.datasource.remote.RemoteDataSourceImpl
import com.thesomeshkumar.flixplorer.data.repository.FlixplorerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
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
