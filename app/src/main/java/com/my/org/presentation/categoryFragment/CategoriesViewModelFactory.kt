package com.my.org.presentation.categoryFragment

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.my.org.data.data_source.AppDatabase
import com.my.org.data.repository.CategoriesRepositoryImpl
import com.my.org.domain.usecase.categoryUseCases.DeleteCategoryUseCase
import com.my.org.domain.usecase.categoryUseCases.GetAllCategoriesUseCase
import com.my.org.domain.usecase.categoryUseCases.InsertCategoryUseCase
import com.my.org.domain.usecase.categoryUseCases.UpdateCategoryUseCase

class CategoriesViewModelFactory(context: Context): ViewModelProvider.Factory {
    val repository by lazy { CategoriesRepositoryImpl(AppDatabase.getDatabase(context = context).getAppDao()) }
    val getAllCategoriesUseCase by lazy { GetAllCategoriesUseCase(repository) }
    val insertCategoriesUseCase by lazy { InsertCategoryUseCase(repository) }
    val updateCategoryUseCase by lazy { UpdateCategoryUseCase(repository) }
    val deleteCategoryUseCase by lazy { DeleteCategoryUseCase(repository) }
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoriesViewModel(
            getAllCategoriesUseCase = getAllCategoriesUseCase,
            insertCategoryUseCase = insertCategoriesUseCase,
            updateCategoryUseCase = updateCategoryUseCase,
            deleteCategoryUseCase = deleteCategoryUseCase
        ) as T
    }
}