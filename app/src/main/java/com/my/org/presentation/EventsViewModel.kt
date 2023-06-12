package com.my.org.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.org.data.data_source.EventsDatabase
import com.my.org.domain.models.Event
import com.my.org.domain.repository.EventRepository
import com.my.org.domain.usecase.DeleteEventUseCase
import com.my.org.domain.usecase.GetAllEventsUseCase
import com.my.org.domain.usecase.InsertEventUseCase
import com.my.org.domain.usecase.UpdateEventUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventsViewModel(private val getAllEventsUseCase: GetAllEventsUseCase,
private val insertEventUseCase: InsertEventUseCase,
private val updateEventUseCase: UpdateEventUseCase,
private val deleteEventUseCase: DeleteEventUseCase): ViewModel() {


    val eventsLiveData: LiveData<List<Event>> = getAllEventsUseCase.execute()


    fun insertEvent(event: Event) = viewModelScope.launch(Dispatchers.IO) {
        insertEventUseCase.execute(event)
    }

    fun updateEvent(event: Event) = viewModelScope.launch(Dispatchers.IO) {
        updateEventUseCase.execute(event)
    }

    fun deleteEvent(event: Event) = viewModelScope.launch(Dispatchers.IO) {
        deleteEventUseCase.execute(event)
    }
}