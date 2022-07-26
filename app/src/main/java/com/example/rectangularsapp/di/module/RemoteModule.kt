package com.example.rectangularsapp.di.module

import com.example.rectangularsapp.remote.api.ApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RemoteModule {

    companion object {
        @Provides
        @Singleton
        fun provideMoviesApiService(retrofit: Retrofit): ApiService {
            return retrofit.create(ApiService::class.java)
        }
    }
}