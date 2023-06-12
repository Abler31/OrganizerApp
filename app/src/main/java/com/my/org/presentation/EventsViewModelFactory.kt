package com.my.org.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.my.org.data.data_source.EventsDatabase
import com.my.org.data.repository.EventsRepositoryImpl
import com.my.org.domain.usecase.DeleteEventUseCase
import com.my.org.domain.usecase.GetAllEventsUseCase
import com.my.org.domain.usecase.InsertEventUseCase
import com.my.org.domain.usecase.UpdateEventUseCase

class EventsViewModelFactory(context: Context): ViewModelProvider.Factory {
    val repository by lazy { EventsRepositoryImpl(EventsDatabase.getDatabase(context = context).getEventsDao()) }
    val getAllEventsUseCase by lazy { GetAllEventsUseCase(repository) }
    val insertEventUseCase by lazy { InsertEventUseCase(repository) }
    val updateEventUseCase by lazy { UpdateEventUseCase(repository) }
    val deleteEventUseCase by lazy { DeleteEventUseCase(repository) }
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EventsViewModel(
            getAllEventsUseCase = getAllEventsUseCase,
            insertEventUseCase = insertEventUseCase,
            updateEventUseCase = updateEventUseCase,
            deleteEventUseCase = deleteEventUseCase
        ) as T
    }
}