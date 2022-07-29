package com.example.rectangularsapp.presentation.viewstate

import com.example.rectangularsapp.presentation.model.RectangleUiModel

sealed class MainViewState {

    object Loading : MainViewState()

    data class Success(val rectangles: List<RectangleUiModel>) : MainViewState()
}