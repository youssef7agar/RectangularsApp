package com.example.rectangularsapp.domain.usecase

import com.example.rectangularsapp.domain.model.Rectangle
import com.example.rectangularsapp.domain.repository.Repository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class GetRectanglesListUseCase @Inject constructor(
    private val repository: Repository
) {

    fun execute(): Single<List<Rectangle>> {
        return repository.getRectanglesList()
            .subscribeOn(Schedulers.io())
    }
}