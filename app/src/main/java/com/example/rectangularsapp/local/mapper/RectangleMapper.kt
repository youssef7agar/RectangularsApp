package com.example.rectangularsapp.local.mapper

import com.example.rectangularsapp.domain.model.Rectangle
import com.example.rectangularsapp.local.model.RectangleLocal
import java.util.concurrent.ThreadLocalRandom
import javax.inject.Inject

class RectangleMapper @Inject constructor() {

    fun mapFromLocal(input: List<RectangleLocal>): List<Rectangle> {

        return input.map {
            Rectangle(
                id = it.randomKey,
                x = it.x,
                y = it.y,
                size = it.size
            )
        }

    }

    fun mapToLocal(input: List<Rectangle>): List<RectangleLocal> {

        return input.map {
            RectangleLocal(
                randomKey = it.id,
                x = it.x,
                y = it.y,
                size = it.size
            )
        }
    }
}