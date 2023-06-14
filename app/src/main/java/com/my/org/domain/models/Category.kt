package com.my.org.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categoriesTable")
data class Category(
    val name: String
) {
    @PrimaryKey(autoGenerate = true) var id = 0
}