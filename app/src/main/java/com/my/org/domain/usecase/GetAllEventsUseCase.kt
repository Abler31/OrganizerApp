package com.my.org.domain.usecase

import androidx.lifecycle.LiveData
import com.my.org.domain.models.Event
import com.my.org.domain.repository.EventRepository

class GetAllEventsUseCase(
    private val repository: EventRepository
) {
    fun execute(): LiveData<List<Event>>{
        return repository.getAllEvents()
    }
}