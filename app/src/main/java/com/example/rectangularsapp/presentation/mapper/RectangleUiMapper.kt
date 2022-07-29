package com.example.rectangularsapp.presentation.mapper

import com.example.rectangularsapp.domain.model.Rectangle
import com.example.rectangularsapp.presentation.model.RectangleUiModel
import javax.inject.Inject

class RectangleUiMapper @Inject constructor() {

    fun map(rectangle: Rectangle, screenWidth: Float, screenHeight: Float): RectangleUiModel {
        return RectangleUiModel(
            x = rectangle.x,
            y = rectangle.y,
            height = rectangle.size * screenHeight,
            width = rectangle.size * screenWidth
        )
    }
}