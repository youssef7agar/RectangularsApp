package com.example.rectangularsapp.domain.usecase

import com.example.rectangularsapp.domain.repository.Repository
import javax.inject.Inject

class GetLatestDayUseCase @Inject constructor(
    private val repository: Repository
) {

    fun execute(): Long {
        return repository.getLatestDay()
    }
}