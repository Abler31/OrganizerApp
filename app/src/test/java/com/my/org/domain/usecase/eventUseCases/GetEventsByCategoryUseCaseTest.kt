package com.my.org.domain.usecase.eventUseCases

import com.my.org.data.FakeEventsRepository
import com.my.org.domain.models.Event
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Assert.*
import org.junit.Test
import java.time.LocalDate

class GetEventsByCategoryUseCaseTest {

    private lateinit var getEventsByCategory: GetEventsByCategoryUseCase
    private lateinit var fakeEventsRepository: FakeEventsRepository

    @Before
    fun setUp() {
        fakeEventsRepository = FakeEventsRepository()
        getEventsByCategory = GetEventsByCategoryUseCase(fakeEventsRepository)

        val eventsToInsert = mutableListOf<Event>()
        (1..5).forEach { c ->
            eventsToInsert.add(
                Event(
                    text = c.toString(),
                    description = c.toString(),
                    time = c.toString(),
                    category = c.toString(),
                    date = LocalDate.of(2020, 1, 1)
                )
            )
        }
        runBlocking {
            eventsToInsert.forEach{fakeEventsRepository.insert(it)}
        }

    }

    @Test
    fun `return the same data as in repository`() = runBlocking{
        val actual = getEventsByCategory.execute("3").first()
        val expected = Event("3", "3", "3", "3", LocalDate.of(2020, 1, 1))

        assertEquals(expected, actual)
    }

}