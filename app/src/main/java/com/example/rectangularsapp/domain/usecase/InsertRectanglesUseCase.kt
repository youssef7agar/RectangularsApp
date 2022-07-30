package com.example.rectangularsapp.domain.usecase

import com.example.rectangularsapp.domain.model.Rectangle
import com.example.rectangularsapp.domain.repository.Repository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class InsertRectanglesUseCase @Inject constructor(
    private val repository: Repository
) {

    fun execute(list: List<Rectangle>): Completable {
        return repository.insertRectangles(list)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}