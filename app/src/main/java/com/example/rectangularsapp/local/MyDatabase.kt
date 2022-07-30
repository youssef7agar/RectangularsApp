package com.example.rectangularsapp.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rectangularsapp.local.dao.RectangleDao
import com.example.rectangularsapp.local.model.RectangleLocal

@Database(
    entities = [
        RectangleLocal::class
    ],
    version = 1
)
abstract class MyDatabase : RoomDatabase() {

    abstract fun rectangleDao(): RectangleDao
}