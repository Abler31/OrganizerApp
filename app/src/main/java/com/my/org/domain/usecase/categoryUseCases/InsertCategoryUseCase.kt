package com.my.org.domain.usecase.categoryUseCases

import com.my.org.domain.models.Category
import com.my.org.domain.repository.CategoriesRepository

class InsertCategoryUseCase(
    private val repository: CategoriesRepository
) {

    suspend fun execute(category: Category){
        repository.insert(category)
    }

}