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

class DeleteEventUseCaseTest {

    private lateinit var deleteEventUseCase: DeleteEventUseCase
    private lateinit var fakeEventsRepository: FakeEventsRepository

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        fakeEventsRepository = FakeEventsRepository()
        deleteEventUseCase = DeleteEventUseCase(fakeEventsRepository)

        val eventsToInsert = mutableListOf<Event>()
        (1..5).forEach{ c ->
            eventsToInsert.add(Event(
                c.toString(),
                c.toString(),
                c.toString(),
                c.toString(),
                date = LocalDate.of(2020, 1, 1)
            ))
        }
        runBlocking {
            eventsToInsert.forEach{fakeEventsRepository.insert(it)}
        }
    }

    @Test
    fun `delete correct event`() = runBlocking{
        val event = Event("2", "2","2","2",LocalDate.of(2020, 1, 1))
        deleteEventUseCase.execute(event)

        assertNull(fakeEventsRepository.getAllEvents().value?.find {
            it.text == "2"
        })
    }

    @Test
    fun `delete correct event, another event untouched`() = runBlocking{
        val event = Event("2", "2","2","2",LocalDate.of(2020, 1, 1))
        deleteEventUseCase.execute(event)

        assertNotNull(fakeEventsRepository.getAllEvents().value?.find {
            it.text == "3"
        })
    }
}