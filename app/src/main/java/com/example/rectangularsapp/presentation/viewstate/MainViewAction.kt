package com.example.rectangularsapp.presentation.viewstate

sealed class MainViewAction {

    data class StoreScreenMetrics(
        val width: Float,
        val height: Float,
        val time: Long
    ) : MainViewAction()

    data class UpdateRectangle1(
        val x: Float,
        val y: Float,
        val time: Long
    ) : MainViewAction()

    data class UpdateRectangle2(
        val x: Float,
        val y: Float,
        val time: Long
    ) : MainViewAction()
}