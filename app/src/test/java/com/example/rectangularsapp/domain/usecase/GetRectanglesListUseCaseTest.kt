package com.example.rectangularsapp.domain.usecase

import com.example.rectangularsapp.domain.model.Rectangle
import com.example.rectangularsapp.domain.repository.Repository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Test

class GetRectanglesListUseCaseTest {

    private val repository = mock<Repository>()
    private val useCase = GetRectanglesListUseCase(
        repository = repository
    )

    @Test
    fun `executing use case calls repository`() {
        stubGetRectanglesList(Single.never())

        useCase.execute()

        verify(repository).getRectanglesList()
    }

    private fun stubGetRectanglesList(single: Single<List<Rectangle>>) {
        whenever(repository.getRectanglesList())
            .thenReturn(single)
    }
}