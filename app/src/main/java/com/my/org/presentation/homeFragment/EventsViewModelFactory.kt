package com.my.org.presentation.homeFragment

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.my.org.data.data_source.AppDatabase
import com.my.org.data.repository.EventsRepositoryImpl
import com.my.org.domain.usecase.eventUseCases.DeleteEventUseCase
import com.my.org.domain.usecase.eventUseCases.GetAllEventsUseCase
import com.my.org.domain.usecase.eventUseCases.GetEventsByDateUseCase
import com.my.org.domain.usecase.eventUseCases.InsertEventUseCase
import com.my.org.domain.usecase.eventUseCases.UpdateEventUseCase

class EventsViewModelFactory(context: Context): ViewModelProvider.Factory {
    val repository by lazy { EventsRepositoryImpl(AppDatabase.getDatabase(context = context).getAppDao()) }
    val getAllEventsUseCase by lazy { GetAllEventsUseCase(repository) }
    val insertEventUseCase by lazy { InsertEventUseCase(repository) }
    val updateEventUseCase by lazy { UpdateEventUseCase(repository) }
    val deleteEventUseCase by lazy { DeleteEventUseCase(repository) }
    val getEventsByDateUseCase by lazy { GetEventsByDateUseCase(repository) }
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EventsViewModel(
            getAllEventsUseCase = getAllEventsUseCase,
            insertEventUseCase = insertEventUseCase,
            updateEventUseCase = updateEventUseCase,
            deleteEventUseCase = deleteEventUseCase,
            getEventsByDateUseCase = getEventsByDateUseCase
        ) as T
    }
}