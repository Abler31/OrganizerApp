package com.my.org.di

import android.content.Context
import com.my.org.data.data_source.AppDao
import com.my.org.data.data_source.AppDatabase
import com.my.org.data.repository.CategoriesRepositoryImpl
import com.my.org.domain.repository.CategoriesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideCategoriesRepository(@ApplicationContext context: Context): CategoriesRepository{
        return CategoriesRepositoryImpl(appDao = AppDatabase
            .getDatabase(context = context).getAppDao())
    }

}