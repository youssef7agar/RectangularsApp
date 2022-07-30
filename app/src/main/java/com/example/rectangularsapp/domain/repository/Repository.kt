package com.example.rectangularsapp.domain.repository

import com.example.rectangularsapp.domain.model.Rectangle
import com.example.rectangularsapp.domain.model.RectangleResponse
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface Repository {

    fun getRectangles(): Single<RectangleResponse>

    fun getRectanglesList(): Single<List<Rectangle>>

    fun insertRectangles(rectangles: List<Rectangle>): Completable

    fun setLatestDay(newLatestDay: Long)

    fun getLatestDay(): Long
}