package com.example.rectangularsapp.domain.repository

import com.example.rectangularsapp.domain.model.Rectangle
import com.example.rectangularsapp.domain.model.RectangleResponse
import com.example.rectangularsapp.local.dao.RectangleDao
import com.example.rectangularsapp.local.mapper.RectangleMapper
import com.example.rectangularsapp.local.model.RectangleLocal
import com.example.rectangularsapp.local.preferences.LatestDaySharedPreferences
import com.example.rectangularsapp.remote.api.ApiService
import com.example.rectangularsapp.remote.mapper.RectangleResponseMapper
import com.example.rectangularsapp.remote.model.RectangleResponseRemote
import com.example.rectangularsapp.remote.repository.RepositoryImpl
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import java.lang.Exception

class RepositoryTest {

    private val apiService = mock<ApiService>()
    private val rectangleDao = mock<RectangleDao>()
    private val latestDaySharedPreferences = mock<LatestDaySharedPreferences>()
    private val rectangleResponseMapper = mock<RectangleResponseMapper>()
    private val rectangleMapper = mock<RectangleMapper>()
    private val repository = RepositoryImpl(
        apiService = apiService,
        rectangleDao = rectangleDao,
        latestDaySharedPreferences = latestDaySharedPreferences,
        rectangleResponseMapper = rectangleResponseMapper,
        rectangleMapper = rectangleMapper
    )

    // this is to cover only the first function in the repo and all others will have almost all 3 test cases

    @Test
    fun`getRectangles calls the getRectangles function in ApiService`() {
        stubGetRectangles(Single.never())

        repository.getRectangles()

        verify(apiService).getRectangles()
    }

    @Test
    fun `A result is returned from getRectangles when all goes well`() {
        val rectangleResponseRemote = RectangleResponseRemote(rectangularList = listOf())
        val rectangleResponse = RectangleResponse(rectangles = listOf())

        stubGetRectangles(Single.just(rectangleResponseRemote))
        stubMapRectangleResponse(rectangleResponse)

        val testObserver = repository.getRectangles().test()

        testObserver.assertValue(rectangleResponse)
    }

    @Test
    fun `an error is returned when something goes wrong fetching rectangles from getRectangles`() {
        val error = Exception()

        stubGetRectangles(Single.error(error))

        val testObserver = repository.getRectangles().test()

        testObserver.assertError(error)
    }

    private fun stubGetRectangles(single: Single<RectangleResponseRemote>) {
        whenever(apiService.getRectangles())
            .thenReturn(single)
    }

    private fun stubGetRectanglesList(single: Single<List<RectangleLocal>>) {
        whenever(rectangleDao.getRectangles())
            .thenReturn(single)
    }

    private fun stubInsertRectanglesList(completable: Completable) {
        whenever(rectangleDao.insertRectangles(any()))
            .thenReturn(completable)
    }

    private fun stubGetLatestDay(time: Long) {
        whenever(latestDaySharedPreferences.getLatestDay())
            .thenReturn(time)
    }

    private fun stubSetLatestDay() {
        whenever(latestDaySharedPreferences.setLatestDay(any()))
            .thenReturn(Unit)
    }

    private fun stubMapRectangleResponse(response: RectangleResponse) {
        whenever(rectangleResponseMapper.map(any()))
            .thenReturn(response)
    }

    private fun stubMapRectangleFromLocal(list: List<Rectangle>) {
        whenever(rectangleMapper.mapFromLocal(any()))
            .thenReturn(list)
    }

    private fun stubMapRectangleToLocal(list: List<RectangleLocal>) {
        whenever(rectangleMapper.mapToLocal(any()))
            .thenReturn(list)
    }
}