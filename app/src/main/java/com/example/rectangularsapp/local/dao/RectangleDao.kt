package com.example.rectangularsapp.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rectangularsapp.local.model.RectangleLocal
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface RectangleDao {

    @Query("SELECT * FROM rectangles_table")
    fun getRectangles(): Single<List<RectangleLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRectangles(rectangles: List<RectangleLocal>): Completable
}