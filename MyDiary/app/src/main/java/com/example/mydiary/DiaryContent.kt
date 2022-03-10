package com.example.mydiary

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.util.*


@Entity(tableName = "my_diary")
class DiaryContent(@ColumnInfo(name = "Heading")val heading: String, @ColumnInfo(name = "date")val date: String, @ColumnInfo(name = "Text")val text: String) {
    @PrimaryKey(autoGenerate = true) var id : Int = 0

}