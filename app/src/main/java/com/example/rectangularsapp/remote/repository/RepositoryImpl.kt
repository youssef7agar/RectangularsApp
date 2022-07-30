package com.example.rectangularsapp.remote.repository

import com.example.rectangularsapp.domain.model.Rectangle
import com.example.rectangularsapp.domain.model.RectangleResponse
import com.example.rectangularsapp.domain.repository.Repository
import com.example.rectangularsapp.local.dao.RectangleDao
import com.example.rectangularsapp.local.mapper.RectangleMapper
import com.example.rectangularsapp.local.preferences.LatestDaySharedPreferences
import com.example.rectangularsapp.remote.api.ApiService
import com.example.rectangularsapp.remote.mapper.RectangleResponseMapper
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val rectangleDao: RectangleDao,
    private val latestDaySharedPreferences: LatestDaySharedPreferences,
    private val rectangleResponseMapper: RectangleResponseMapper,
    private val rectangleMapper: RectangleMapper
) : Repository {

    override fun getRectangles(): Single<RectangleResponse> {
        return apiService.getRectangles().map(rectangleResponseMapper::map)
    }

    override fun getRectanglesList(): Single<List<Rectangle>> {
        return rectangleDao.getRectangles().map { rectangleMapper.mapFromLocal(it) }
    }

    override fun insertRectangles(rectangles: List<Rectangle>): Completable {
        return rectangleDao.insertRectangles(rectangleMapper.mapToLocal(rectangles))
    }

    override fun setLatestDay(newLatestDay: Long) {
        latestDaySharedPreferences.setLatestDay(newLatestDay)
    }

    override fun getLatestDay(): Long {
        return latestDaySharedPreferences.getLatestDay()
    }
}