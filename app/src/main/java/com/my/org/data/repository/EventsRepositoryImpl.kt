package com.my.org.data.repository

import androidx.lifecycle.LiveData
import com.my.org.domain.models.Event
import com.my.org.data.data_source.AppDao
import com.my.org.domain.repository.EventRepository
import java.time.LocalDate

class EventsRepositoryImpl(private val appDao: AppDao) : EventRepository {
    override suspend fun insert(event: Event) {
        appDao.insertEvent(event)
    }

    override suspend fun update(event: Event) {
        appDao.updateEvent(event)
    }

    override suspend fun delete(event: Event) {
        appDao.deleteEvent(event)
    }

    override fun getAllEvents(): LiveData<List<Event>> {
        return appDao.getAllEvents()
    }

    override suspend fun getEventsByDate(date: LocalDate): List<Event> {
        return appDao.getEventsByDate(date)
    }

    override suspend fun getEventsByCategory(category: String): List<Event> {
        return appDao.getEventsByCategory(category)
    }


}