package com.my.org.data.data_source

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.my.org.domain.models.Category
import com.my.org.domain.models.Event
import java.time.LocalDate

@Dao
interface AppDao {
    @Insert
    suspend fun insertEvent(event: Event)

    @Update
    suspend fun updateEvent(event: Event)

    @Delete
    suspend fun deleteEvent(event: Event)

    @Query("Select * from eventsTable order by id ASC")
    fun getAllEvents(): LiveData<List<Event>>

    @Query("Select * from eventsTable where date = :date")
    suspend fun getEventsByDate(date: LocalDate): List<Event>

    @Query("Select * from eventsTable where category = :category")
    suspend fun getEventsByCategory(category: String): List<Event>

    @Insert
    suspend fun insertCategory(category: Category)

    @Update
    suspend fun updateCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("Select * from categoriesTable order by id ASC")
    fun getAllCategories(): LiveData<List<Category>>
}