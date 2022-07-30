package com.example.rectangularsapp.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rectangles_table")
data class RectangleLocal(
    @PrimaryKey
    val randomKey: Long,
    val x: Float,
    val y: Float,
    val size: Float
)