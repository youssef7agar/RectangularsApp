package com.example.rectangularsapp.domain.repository

import com.example.rectangularsapp.domain.model.RectangleResponse
import io.reactivex.rxjava3.core.Single

interface Repository {

    fun getRectangles(): Single<RectangleResponse>
}