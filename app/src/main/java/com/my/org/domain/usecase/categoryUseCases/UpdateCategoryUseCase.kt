package com.my.org.domain.usecase.categoryUseCases

import com.my.org.domain.models.Category
import com.my.org.domain.repository.CategoriesRepository

class UpdateCategoryUseCase(
    private val repository: CategoriesRepository
) {

    suspend fun execute(category: Category) {
        repository.update(category)
    }
}