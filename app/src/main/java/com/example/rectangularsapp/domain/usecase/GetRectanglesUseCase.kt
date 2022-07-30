package com.example.rectangularsapp.domain.usecase

import com.example.rectangularsapp.domain.model.Rectangle
import com.example.rectangularsapp.domain.model.RectangleResponse
import com.example.rectangularsapp.domain.repository.Repository
import io.reactivex.rxjava3.core.Single
import java.util.concurrent.ThreadLocalRandom
import javax.inject.Inject

class GetRectanglesUseCase @Inject constructor(
    private val repository: Repository
) {
    fun execute(): Single<RectangleResponse> {
        return Single.just(
            RectangleResponse(
                rectangles = listOf(
                    Rectangle(
                        id = ThreadLocalRandom.current().nextInt(0, 1000 + 1).toLong(),
                        x = 0.5f,
                        y = 0.5f,
                        size = 0.3f
                    ),
                    Rectangle(
                        id = ThreadLocalRandom.current().nextInt(0, 1000 + 1).toLong(),
                        x = 0.7f,
                        y = 0.7f,
                        size = 0.3f
                    )
                )
            )
        )
//        repository.getRectangles()
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeOn(Schedulers.io())
    }
}