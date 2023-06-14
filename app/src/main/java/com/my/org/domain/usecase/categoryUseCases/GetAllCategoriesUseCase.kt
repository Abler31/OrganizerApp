package com.my.org.domain.usecase.categoryUseCases

import androidx.lifecycle.LiveData
import com.my.org.domain.models.Category
import com.my.org.domain.repository.CategoriesRepository

class GetAllCategoriesUseCase(
    private val repository: CategoriesRepository
) {

    fun execute(): LiveData<List<Category>>{
        return repository.getAllEvents()
    }

}