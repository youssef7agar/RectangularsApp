package com.example.rectangularsapp.remote.model

import com.google.gson.annotations.SerializedName

data class RectangleResponseRemote(
    @SerializedName("rectangles") val rectangularList: List<RectangleRemote>?
)