package com.example.rectangularsapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rectangularsapp.domain.model.Rectangle
import com.example.rectangularsapp.domain.model.RectangleResponse
import com.example.rectangularsapp.domain.usecase.*
import com.example.rectangularsapp.presentation.mapper.RectangleUiMapper
import com.example.rectangularsapp.presentation.viewstate.MainViewAction
import com.example.rectangularsapp.presentation.viewstate.MainViewState
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getRectanglesUseCase: GetRectanglesUseCase,
    private val insertRectanglesUseCase: InsertRectanglesUseCase,
    private val getRectanglesListUseCase: GetRectanglesListUseCase,
    private val setLatestDayUseCase: SetLatestDayUseCase,
    private val getLatestDayUseCase: GetLatestDayUseCase,
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
                height = action.height,
                time = action.time
            )
            is MainViewAction.UpdateRectangle1 -> updateRectangle1(
                x = action.x,
                y = action.y,
                time = action.time
            )
            is MainViewAction.UpdateRectangle2 -> updateRectangle2(
                x = action.x,
                y = action.y,
                time = action.time
            )
        }
    }

    private fun storeMetrics(width: Float, height: Float, time: Long) {
        screenWidth = width
        screenHeight = height

        getRectangles(time)
    }

    private fun getRectangles(time: Long) {
        if (getLatestDayUseCase.execute() == -1L || time - getLatestDayUseCase.execute() >= 60000L) {
            getRectanglesUseCase.execute()
                .subscribeWith(GetRectanglesSubscriber())
                .also(compositeDisposable::add)
        } else {
            getRectanglesListUseCase.execute()
                .subscribeWith(GetRectanglesListSubscriber())
                .also(compositeDisposable::add)
        }
    }

    private inner class GetRectanglesSubscriber : DisposableSingleObserver<RectangleResponse>() {
        override fun onSuccess(t: RectangleResponse) {
            rectangles = t.rectangles.toMutableList()
            _viewState.postValue(MainViewState.Success(
                rectangles = rectangles.map { rectangle ->
                    rectangleUiMapper.map(
                        rectangle,
                        screenWidth,
                        screenHeight
                    )
                }
            ))
        }

        override fun onError(e: Throwable) {
            Timber.w("Something went wrong whilst loading the rectangles: $e")
        }
    }

    private inner class GetRectanglesListSubscriber : DisposableSingleObserver<List<Rectangle>>() {
        override fun onSuccess(t: List<Rectangle>) {
            rectangles = t.toMutableList()
            _viewState.postValue(MainViewState.Success(
                rectangles = rectangles.map { rectangle ->
                    rectangleUiMapper.map(
                        rectangle,
                        screenWidth,
                        screenHeight
                    )
                }
            ))
        }

        override fun onError(e: Throwable) {
            Timber.w("Something went wrong whilst getting the rectangles: $e")
        }
    }

    private fun updateRectangle1(x: Float, y: Float, time: Long) {
        rectangles[0] = rectangles[0].copy(x = x, y = y)
        _viewState.postValue(MainViewState.Success(
            rectangles = rectangles.map { rectangle ->
                rectangleUiMapper.map(
                    rectangle,
                    screenWidth,
                    screenHeight
                )
            }
        ))

        Observable.fromArray(rectangles)
            .debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                insertRectanglesUseCase.execute(it).subscribe().also(compositeDisposable::add)
                Observable.fromArray(rectangles)
            }
            .subscribe {
                setLatestDayUseCase.execute(time)
            }
    }

    private fun updateRectangle2(x: Float, y: Float, time: Long) {
        rectangles[1] = rectangles[1].copy(x = x, y = y)
        _viewState.postValue(MainViewState.Success(
            rectangles = rectangles.map { rectangle ->
                rectangleUiMapper.map(
                    rectangle,
                    screenWidth,
                    screenHeight
                )
            }
        ))

        Observable.fromArray(rectangles)
            .debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                insertRectanglesUseCase.execute(it).subscribe().also(compositeDisposable::add)
                Observable.fromArray(rectangles)
            }
            .subscribe {
                setLatestDayUseCase.execute(time)
            }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}