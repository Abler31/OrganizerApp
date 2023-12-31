package com.my.org.domain.usecase.eventUseCases

import com.my.org.domain.models.Event
import com.my.org.domain.repository.EventRepository

class InsertEventUseCase(
    private val repository: EventRepository
) {
    suspend fun execute(event: Event){
        repository.insert(event)
    }
}