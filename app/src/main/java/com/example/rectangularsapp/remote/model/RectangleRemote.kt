package com.example.rectangularsapp.remote.model

import com.google.gson.annotations.SerializedName

data class RectangleRemote(
    @SerializedName("x") val x: Float?,
    @SerializedName("y") val y: Float?,
    @SerializedName("size") val size: Float?
)