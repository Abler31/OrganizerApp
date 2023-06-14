package com.my.org.data.repository

import androidx.lifecycle.LiveData
import com.my.org.data.data_source.AppDao
import com.my.org.domain.models.Category
import com.my.org.domain.repository.CategoriesRepository

class CategoriesRepositoryImpl(private val appDao: AppDao) : CategoriesRepository {
    override suspend fun insert(category: Category) {
        appDao.insertCategory(category)
    }

    override suspend fun update(category: Category) {
        appDao.updateCategory(category)
    }

    override suspend fun delete(category: Category) {
        appDao.deleteCategory(category)
    }

    override fun getAllEvents(): LiveData<List<Category>> {
        return appDao.getAllCategories()
    }


}