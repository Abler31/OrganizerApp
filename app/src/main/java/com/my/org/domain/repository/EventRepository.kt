package com.my.org.domain.repository

import androidx.lifecycle.LiveData
import com.my.org.domain.models.Event

interface EventRepository {

    suspend fun insert(event: Event)

    suspend fun update(event: Event)

    suspend fun delete(event: Event)

    fun getAllEvents(): LiveData<List<Event>>
}