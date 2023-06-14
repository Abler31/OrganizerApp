package com.my.org.domain.repository

import androidx.lifecycle.LiveData
import com.my.org.domain.models.Category
import com.my.org.domain.models.Event

interface CategoriesRepository {

    suspend fun insert(category: Category)

    suspend fun update(category: Category)

    suspend fun delete(category: Category)

    fun getAllEvents(): LiveData<List<Category>>
}