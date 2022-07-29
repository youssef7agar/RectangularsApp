package com.example.rectangularsapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rectangularsapp.domain.model.Rectangle
import com.example.rectangularsapp.domain.model.RectangleResponse
import com.example.rectangularsapp.domain.usecase.GetRectanglesUseCase
import com.example.rectangularsapp.presentation.mapper.RectangleUiMapper
import com.example.rectangularsapp.presentation.viewstate.MainViewAction
import com.example.rectangularsapp.presentation.viewstate.MainViewState
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getRectanglesUseCase: GetRectanglesUseCase,
    private val rectangleUiMapper: RectangleUiMapper
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _viewState = MutableLiveData<MainViewState>()
    val viewState: LiveData<MainViewState>
        get() = _viewState

    private lateinit var rectangles: MutableList<Rectangle>

    private var screenWidth: Float = -1f
    private var screenHeight: Float = -1f

    init {
        _viewState.postValue(MainViewState.Loading)
    }

    fun postAction(action: MainViewAction) {
        when (action) {
            is MainViewAction.StoreScreenMetrics -> storeMetrics(
                width = action.width,
                height = action.height
            )
            is MainViewAction.UpdateRectangle1 -> updateRectangle1(
                x = action.x,
                y = action.y
            )
            is MainViewAction.UpdateRectangle2 -> updateRectangle2(
                x = action.x,
                y = action.y
            )
        }
    }

    private fun storeMetrics(width: Float, height: Float) {
        screenWidth = width
        screenHeight = height

        getRectangles()
    }

    private fun getRectangles() {
        getRectanglesUseCase.execute()
            .subscribeWith(GetRectanglesSubscriber())
            .also(compositeDisposable::add)
    }

    private inner class GetRectanglesSubscriber : DisposableSingleObserver<RectangleResponse>() {
        override fun onSuccess(t: RectangleResponse) {
            rectangles = t.rectangles.toMutableList()
            _viewState.postValue(MainViewState.Success(
                rectangles = rectangles.map{rectangle -> rectangleUiMapper.map(rectangle, screenWidth, screenHeight) }
            ))
        }

        override fun onError(e: Throwable) {
            Timber.w("Something went wrong whilst loading the rectangles: $e")
        }
    }

    private fun updateRectangle1(x: Float, y: Float) {
        rectangles[0] = rectangles[0].copy(x = x, y = y)
        _viewState.postValue(MainViewState.Success(
            rectangles = rectangles.map{rectangle -> rectangleUiMapper.map(rectangle, screenWidth, screenHeight) }
        ))
    }

    private fun updateRectangle2(x: Float, y: Float) {
        rectangles[1] = rectangles[1].copy(x = x, y = y)
        _viewState.postValue(MainViewState.Success(
            rectangles = rectangles.map{rectangle -> rectangleUiMapper.map(rectangle, screenWidth, screenHeight) }
        ))
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}