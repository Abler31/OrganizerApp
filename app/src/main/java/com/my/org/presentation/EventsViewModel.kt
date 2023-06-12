package com.my.org.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.org.domain.models.Event
import com.my.org.domain.usecase.DeleteEventUseCase
import com.my.org.domain.usecase.GetAllEventsUseCase
import com.my.org.domain.usecase.GetEventsByDateUseCase
import com.my.org.domain.usecase.InsertEventUseCase
import com.my.org.domain.usecase.UpdateEventUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class EventsViewModel(private val getAllEventsUseCase: GetAllEventsUseCase,
private val insertEventUseCase: InsertEventUseCase,
private val updateEventUseCase: UpdateEventUseCase,
private val deleteEventUseCase: DeleteEventUseCase,
private val getEventsByDateUseCase: GetEventsByDateUseCase
): ViewModel() {


    val eventsLiveData: LiveData<List<Event>> = getAllEventsUseCase.execute()
    val _eventsByDate = MutableLiveData<List<Event>>()
    val eventsByDate: LiveData<List<Event>>
        get() = _eventsByDate


    fun insertEvent(event: Event) = viewModelScope.launch(Dispatchers.IO) {
        insertEventUseCase.execute(event)
    }

    fun updateEvent(event: Event) = viewModelScope.launch(Dispatchers.IO) {
        updateEventUseCase.execute(event)
    }

    fun deleteEvent(event: Event) = viewModelScope.launch(Dispatchers.IO) {
        deleteEventUseCase.execute(event)
    }

    fun getEventsByDate(date: LocalDate) = viewModelScope.launch(Dispatchers.IO) {
        _eventsByDate.postValue(getEventsByDateUseCase.execute(date))
    }
}