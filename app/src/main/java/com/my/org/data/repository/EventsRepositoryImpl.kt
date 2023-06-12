package com.my.org.data.repository

import androidx.lifecycle.LiveData
import com.my.org.domain.models.Event
import com.my.org.data.data_source.EventsDao
import com.my.org.domain.repository.EventRepository

class EventsRepositoryImpl(private val eventsDao: EventsDao) : EventRepository {
    override suspend fun insert(event: Event) {
        eventsDao.insert(event)
    }

    override suspend fun update(event: Event) {
        eventsDao.update(event)
    }

    override suspend fun delete(event: Event) {
        eventsDao.delete(event)
    }

    override fun getAllEvents(): LiveData<List<Event>> {
        return eventsDao.getAllEvents()
    }


}