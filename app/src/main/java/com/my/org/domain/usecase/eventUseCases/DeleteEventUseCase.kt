package com.my.org.domain.usecase.eventUseCases

import com.my.org.domain.models.Event
import com.my.org.domain.repository.EventRepository

class DeleteEventUseCase(
    private val repository: EventRepository
) {
    suspend fun execute(event: Event){
        repository.delete(event)
    }
}