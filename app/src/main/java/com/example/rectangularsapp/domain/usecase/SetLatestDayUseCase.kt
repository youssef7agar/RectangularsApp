package com.example.rectangularsapp.domain.usecase

import com.example.rectangularsapp.domain.repository.Repository
import javax.inject.Inject

class SetLatestDayUseCase @Inject constructor(
    private val repository: Repository
) {

    fun execute(latestDay: Long) {
        repository.setLatestDay(latestDay)
    }
}