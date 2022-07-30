package com.example.rectangularsapp.di.module

import com.example.rectangularsapp.local.MyDatabase
import com.example.rectangularsapp.local.dao.RectangleDao
import dagger.Module
import dagger.Provides

@Module
object LocalModule {

    @Provides
    fun provideRectanglesDao(db: MyDatabase): RectangleDao {
        return db.rectangleDao()
    }

}