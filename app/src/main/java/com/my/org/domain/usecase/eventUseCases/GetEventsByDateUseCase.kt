package com.my.org.domain.usecase.eventUseCases

import androidx.lifecycle.LiveData
import com.my.org.domain.models.Event
import com.my.org.domain.repository.EventRepository
import java.time.LocalDate

class GetEventsByDateUseCase( private val repository: EventRepository
) {
    suspend fun execute(date: LocalDate): List<Event> {
        return repository.getEventsByDate(date)
    }
}