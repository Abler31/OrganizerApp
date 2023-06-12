package com.my.org.data.data_source

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.my.org.domain.models.Event

@Dao
interface EventsDao {
    @Insert
    suspend fun insert(event: Event)

    @Update
    suspend fun update(event: Event)

    @Delete
    suspend fun delete(event: Event)

    @Query("Select * from eventsTable order by id ASC")
    fun getAllEvents(): LiveData<List<Event>>
}