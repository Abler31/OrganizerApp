package com.my.org.domain.usecase.eventUseCases

import com.my.org.domain.models.Event
import com.my.org.domain.repository.EventRepository
import java.time.LocalDate

class GetEventsByCategoryUseCase(private val repository: EventRepository
) {
    suspend fun execute(category: String): List<Event> {
        return repository.getEventsByCategory(category)
    }
}