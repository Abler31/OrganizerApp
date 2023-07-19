package com.my.org.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.my.org.domain.models.Event
import com.my.org.domain.repository.EventRepository
import java.time.LocalDate

class FakeEventsRepository: EventRepository {


    private val events = mutableListOf<Event>()

    override suspend fun insert(event: Event) {
        events.add(event)
    }

    override suspend fun update(event: Event) {
        val index = events.indexOf(events.find {
            it.id == event.id
        })
        events[index] = event
    }

    override suspend fun delete(event: Event) {
        events.remove(event)
    }

    override fun getAllEvents(): LiveData<List<Event>> {
        val allEvents = MutableLiveData<List<Event>>()
        allEvents.value = events
        return allEvents
    }

    override suspend fun getEventsByDate(date: LocalDate): List<Event> {
        return events.filter {
            it.date == date
        }
    }

    override suspend fun getEventsByCategory(category: String): List<Event> {
        return events.filter {
            it.category == category
        }
    }
}