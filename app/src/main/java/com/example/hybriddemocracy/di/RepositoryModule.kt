package com.example.hybriddemocracy

import com.example.hybriddemocracy.data.datasource.remote.ApiService
import com.example.hybriddemocracy.data.datasource.remote.ApiUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.example.hybriddemocracy.data.repository.RepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideRepositoryImpl(api: ApiService): RepositoryImpl {
        return RepositoryImpl(api)
    }
}