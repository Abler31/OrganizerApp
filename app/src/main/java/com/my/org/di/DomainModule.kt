package com.my.org.di

import com.my.org.domain.repository.CategoriesRepository
import com.my.org.domain.usecase.categoryUseCases.DeleteCategoryUseCase
import com.my.org.domain.usecase.categoryUseCases.GetAllCategoriesUseCase
import com.my.org.domain.usecase.categoryUseCases.InsertCategoryUseCase
import com.my.org.domain.usecase.categoryUseCases.UpdateCategoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetAllCategoriesUseCase(categoriesRepository: CategoriesRepository): GetAllCategoriesUseCase{
        return GetAllCategoriesUseCase(repository = categoriesRepository)
    }

    @Provides
    fun provideInsertCategoriesUseCase(categoriesRepository: CategoriesRepository): InsertCategoryUseCase{
        return InsertCategoryUseCase(repository = categoriesRepository)
    }

    @Provides
    fun provideUpdateCategoryUseCase(categoriesRepository: CategoriesRepository): UpdateCategoryUseCase{
        return UpdateCategoryUseCase(repository = categoriesRepository)
    }

    @Provides
    fun provideDeleteCategoryUseCase(categoriesRepository: CategoriesRepository): DeleteCategoryUseCase{
        return DeleteCategoryUseCase(repository = categoriesRepository)
    }
}