package com.my.org.presentation.categoryFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.org.domain.models.Category
import com.my.org.domain.models.Event
import com.my.org.domain.usecase.categoryUseCases.DeleteCategoryUseCase
import com.my.org.domain.usecase.categoryUseCases.GetAllCategoriesUseCase
import com.my.org.domain.usecase.categoryUseCases.InsertCategoryUseCase
import com.my.org.domain.usecase.categoryUseCases.UpdateCategoryUseCase
import com.my.org.domain.usecase.eventUseCases.DeleteEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val insertCategoryUseCase: InsertCategoryUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase
) : ViewModel() {

    val categoriesLiveData: LiveData<List<Category>> = getAllCategoriesUseCase.execute()

    fun insertCategory(category: Category) = viewModelScope.launch(Dispatchers.IO) {
        insertCategoryUseCase.execute(category)
    }

    fun updateCategory(category: Category) = viewModelScope.launch(Dispatchers.IO) {
        updateCategoryUseCase.execute(category)
    }

    fun deleteCategory(category: Category) = viewModelScope.launch(Dispatchers.IO) {
        deleteCategoryUseCase.execute(category)
    }

}