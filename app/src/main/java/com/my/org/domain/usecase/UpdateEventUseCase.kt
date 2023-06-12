package com.my.org.domain.usecase

import com.my.org.domain.models.Event
import com.my.org.domain.repository.EventRepository

class UpdateEventUseCase(
    private val repository: EventRepository
) {
    suspend fun execute(event: Event){
        repository.update(event)
    }
}