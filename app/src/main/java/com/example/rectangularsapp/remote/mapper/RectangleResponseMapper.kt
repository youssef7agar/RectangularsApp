package com.example.rectangularsapp.remote.mapper

import com.example.rectangularsapp.domain.model.Rectangle
import com.example.rectangularsapp.domain.model.RectangleResponse
import com.example.rectangularsapp.remote.model.RectangleRemote
import com.example.rectangularsapp.remote.model.RectangleResponseRemote
import java.util.concurrent.ThreadLocalRandom
import javax.inject.Inject

class RectangleResponseMapper @Inject constructor() {

    fun map(input: RectangleResponseRemote): RectangleResponse {
        assertEssentialParams(input)

        return RectangleResponse(
            rectangles = input.rectangularList!!.map(::map)
        )
    }

    private fun assertEssentialParams(remote: RectangleResponseRemote) {
        when (remote.rectangularList) {
            null -> {
                throw RuntimeException("The params: rectangularList is missing in received object: $remote")
            }
        }
    }

    private fun map(input: RectangleRemote): Rectangle {
        assertEssentialParams(input)

        return Rectangle(
            id = ThreadLocalRandom.current().nextInt(0, 1000 + 1).toLong(),
            x = input.x!!,
            y = input.y!!,
            size = input.size!!
        )
    }

    private fun assertEssentialParams(remote: RectangleRemote) {
        when {
            remote.x == null -> {
                throw RuntimeException("The params: Rectangle x is missing in received object: $remote")
            }
            remote.y == null -> {
                throw RuntimeException("The params: Rectangle y is missing in received object: $remote")
            }
            remote.size == null -> {
                throw RuntimeException("The params: Rectangle size is missing in received object: $remote")
            }
        }
    }
}