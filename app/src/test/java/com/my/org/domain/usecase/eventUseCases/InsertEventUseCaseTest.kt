package com.my.org.domain.usecase.eventUseCases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.my.org.data.FakeEventsRepository
import com.my.org.domain.models.Event
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.time.LocalDate

class InsertEventUseCaseTest {

    private lateinit var fakeEventsRepository: FakeEventsRepository
    private lateinit var insertEventUseCase: InsertEventUseCase

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        fakeEventsRepository = FakeEventsRepository()
        insertEventUseCase = InsertEventUseCase(fakeEventsRepository)
    }

    @Test
    fun `insert is successful`() = runBlocking{
        val event = Event("2", "2","2","2",LocalDate.of(2020, 1, 1))
        insertEventUseCase.execute(event)

        assertNotNull(fakeEventsRepository.getAllEvents().value?.find {
            it.text == "2"
        })
    }
}