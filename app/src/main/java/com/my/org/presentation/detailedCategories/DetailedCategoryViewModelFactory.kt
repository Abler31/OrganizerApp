package com.my.org.presentation.detailedCategories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.my.org.data.data_source.AppDatabase
import com.my.org.data.repository.EventsRepositoryImpl
import com.my.org.domain.usecase.eventUseCases.DeleteEventUseCase
import com.my.org.domain.usecase.eventUseCases.GetAllEventsUseCase
import com.my.org.domain.usecase.eventUseCases.GetEventsByCategoryUseCase
import com.my.org.domain.usecase.eventUseCases.GetEventsByDateUseCase
import com.my.org.domain.usecase.eventUseCases.InsertEventUseCase
import com.my.org.domain.usecase.eventUseCases.UpdateEventUseCase
import com.my.org.presentation.homeFragment.EventsViewModel

class DetailedCategoryViewModelFactory(context: Context): ViewModelProvider.Factory {
    val repository by lazy { EventsRepositoryImpl(AppDatabase.getDatabase(context = context).getAppDao()) }
    val getEventsByCategoryUseCase by lazy { GetEventsByCategoryUseCase(repository) }
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailedCategoryViewModel(
            getEventsByCategoryUseCase = getEventsByCategoryUseCase
        ) as T
    }
}