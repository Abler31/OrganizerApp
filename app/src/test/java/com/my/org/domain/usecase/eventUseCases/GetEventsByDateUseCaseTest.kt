package com.my.org.domain.usecase.eventUseCases

import com.my.org.data.FakeEventsRepository
import com.my.org.domain.models.Event
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class GetEventsByDateUseCaseTest {

    private lateinit var getEventsByDate: GetEventsByDateUseCase
    private lateinit var fakeEventsRepository: FakeEventsRepository

    @Before
    fun setUp() {
        fakeEventsRepository = FakeEventsRepository()
        getEventsByDate = GetEventsByDateUseCase(fakeEventsRepository)

        val eventsToInsert = mutableListOf<Event>()
        (1..5).forEach{c ->
            eventsToInsert.add(
                Event(
                    text = c.toString(),
                    description = c.toString(),
                    time = c.toString(),
                    category = c.toString(),
                    date = LocalDate.of(2020, c, 1)
                )
            )
        }
        runBlocking {
            eventsToInsert.forEach{fakeEventsRepository.insert(it)}
        }

    }

    @Test
    fun `return the same data as in repository`() = runBlocking{
        val actual = getEventsByDate.execute(LocalDate.of(2020, 2, 1)).first()
        val expected = Event("2", "2", "2", "2", LocalDate.of(2020, 2, 1))

        assertEquals(expected, actual)
    }
}