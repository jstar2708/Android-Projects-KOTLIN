package com.example.notes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_notes")
class Note(@ColumnInfo(name = "Text")val text: String) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}