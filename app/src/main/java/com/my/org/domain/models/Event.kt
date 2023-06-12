package com.my.org.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.my.org.data.Converters
import java.time.LocalDate

@Entity(tableName = "eventsTable")
data class Event(val text: String,
                 val date: LocalDate){
    @PrimaryKey(autoGenerate = true) var id = 0
}