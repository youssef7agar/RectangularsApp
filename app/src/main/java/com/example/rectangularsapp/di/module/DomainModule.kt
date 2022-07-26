package com.example.rectangularsapp.di.module

import com.example.rectangularsapp.domain.repository.Repository
import com.example.rectangularsapp.remote.repository.RepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class DomainModule {

    @Binds
    abstract fun bindRepository(repositoryImpl: RepositoryImpl): Repository
}