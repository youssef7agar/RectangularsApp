package com.example.rectangularsapp.remote.api

import com.example.rectangularsapp.remote.model.RectangleResponseRemote
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface ApiService {

    @GET("/resourcer/v1/rectangles")
    fun getRectangles(): Single<RectangleResponseRemote>
}