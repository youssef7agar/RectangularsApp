package com.example.rectangularsapp.remote.repository

import com.example.rectangularsapp.domain.model.RectangleResponse
import com.example.rectangularsapp.domain.repository.Repository
import com.example.rectangularsapp.remote.api.ApiService
import com.example.rectangularsapp.remote.mapper.RectangleResponseMapper
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val rectangleResponseMapper: RectangleResponseMapper
) : Repository {

    override fun getRectangles(): Single<RectangleResponse> {
        return apiService.getRectangles().map(rectangleResponseMapper::map)
    }
}