package com.my.org.presentation.detailedCategories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.org.domain.models.Category
import com.my.org.domain.models.Event
import com.my.org.domain.usecase.categoryUseCases.DeleteCategoryUseCase
import com.my.org.domain.usecase.categoryUseCases.GetAllCategoriesUseCase
import com.my.org.domain.usecase.categoryUseCases.InsertCategoryUseCase
import com.my.org.domain.usecase.categoryUseCases.UpdateCategoryUseCase
import com.my.org.domain.usecase.eventUseCases.DeleteEventUseCase
import com.my.org.domain.usecase.eventUseCases.GetAllEventsUseCase
import com.my.org.domain.usecase.eventUseCases.GetEventsByCategoryUseCase
import com.my.org.domain.usecase.eventUseCases.GetEventsByDateUseCase
import com.my.org.domain.usecase.eventUseCases.InsertEventUseCase
import com.my.org.domain.usecase.eventUseCases.UpdateEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DetailedCategoryViewModel @Inject constructor(
    private val getEventsByCategoryUseCase: GetEventsByCategoryUseCase,
    private val getAllEventsUseCase: GetAllEventsUseCase
) : ViewModel() {

    val eventsLiveData: LiveData<List<Event>> = getAllEventsUseCase.execute()

    fun getEventsByCategory(category: String): List<Event> {
        return eventsLiveData.value?.filter {
            it.category == category
        } ?: emptyList()
    }
}