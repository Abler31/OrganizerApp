package com.my.org.di

import com.my.org.domain.repository.CategoriesRepository
import com.my.org.domain.repository.EventRepository
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
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetAllCategoriesUseCase(categoriesRepository: CategoriesRepository): GetAllCategoriesUseCase {
        return GetAllCategoriesUseCase(repository = categoriesRepository)
    }

    @Provides
    fun provideInsertCategoriesUseCase(categoriesRepository: CategoriesRepository): InsertCategoryUseCase {
        return InsertCategoryUseCase(repository = categoriesRepository)
    }

    @Provides
    fun provideUpdateCategoryUseCase(categoriesRepository: CategoriesRepository): UpdateCategoryUseCase {
        return UpdateCategoryUseCase(repository = categoriesRepository)
    }

    @Provides
    fun provideDeleteCategoryUseCase(categoriesRepository: CategoriesRepository): DeleteCategoryUseCase {
        return DeleteCategoryUseCase(repository = categoriesRepository)
    }

    @Provides
    fun provideGetAllEventsUseCase(eventsRepository: EventRepository): GetAllEventsUseCase {
        return GetAllEventsUseCase(repository = eventsRepository)
    }

    @Provides
    fun provideInsertEventUseCase(eventsRepository: EventRepository): InsertEventUseCase {
        return InsertEventUseCase(repository = eventsRepository)
    }

    @Provides
    fun provideUpdateEventUseCase(eventsRepository: EventRepository): UpdateEventUseCase {
        return UpdateEventUseCase(repository = eventsRepository)
    }

    @Provides
    fun provideDeleteEventUseCase(eventsRepository: EventRepository): DeleteEventUseCase {
        return DeleteEventUseCase(repository = eventsRepository)
    }

    @Provides
    fun provideGetEventsByDateUseCase(eventsRepository: EventRepository): GetEventsByDateUseCase {
        return GetEventsByDateUseCase(repository = eventsRepository)
    }

    @Provides
    fun provideGetEventsByCategoryUseCase(eventsRepository: EventRepository): GetEventsByCategoryUseCase {
        return GetEventsByCategoryUseCase(repository = eventsRepository)
    }

}