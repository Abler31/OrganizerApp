package com.my.org.presentation.homeFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.org.domain.models.Event
import com.my.org.domain.usecase.eventUseCases.DeleteEventUseCase
import com.my.org.domain.usecase.eventUseCases.GetAllEventsUseCase
import com.my.org.domain.usecase.eventUseCases.GetEventsByDateUseCase
import com.my.org.domain.usecase.eventUseCases.InsertEventUseCase
import com.my.org.domain.usecase.eventUseCases.UpdateEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val getAllEventsUseCase: GetAllEventsUseCase,
    private val insertEventUseCase: InsertEventUseCase,
    private val updateEventUseCase: UpdateEventUseCase,
    private val deleteEventUseCase: DeleteEventUseCase,
    private val getEventsByDateUseCase: GetEventsByDateUseCase
) : ViewModel() {


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

    fun getEventsByDate(date: LocalDate): List<Event>{
        return eventsLiveData.value?.filter {
            it.date == date
        } ?: emptyList()
    }
}