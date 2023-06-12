package com.my.org.data.data_source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.my.org.data.Converters
import com.my.org.domain.models.Event

@Database(entities = arrayOf(Event::class), version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class EventsDatabase: RoomDatabase() {
    abstract  fun getEventsDao(): EventsDao

    companion object{
        @Volatile
        private var INSTANCE: EventsDatabase? = null

        fun getDatabase(context: Context): EventsDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EventsDatabase::class.java,
                    "event_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}