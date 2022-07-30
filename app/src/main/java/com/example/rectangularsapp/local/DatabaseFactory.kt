package com.example.rectangularsapp.local

import android.content.Context
import androidx.room.Room

object DatabaseFactory {

    fun createDatabase(context: Context): MyDatabase {
        return createMyDatabaseWithName(context, "myDatabase.db")
    }

    private fun createMyDatabaseWithName(context: Context, name: String): MyDatabase {
        return Room.databaseBuilder(context, MyDatabase::class.java, name)
            .build()
    }
}