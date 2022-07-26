package com.example.rectangularsapp.remote.api

import retrofit2.http.GET

interface ApiService {

    @GET("/resourcer/v1/rectangles")
    fun getRectangulares()
}