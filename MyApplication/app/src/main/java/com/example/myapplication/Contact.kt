package com.example.myapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Contact_Table")
data class Contact(@ColumnInfo(name = "Name")val name: String, @ColumnInfo(name = "Phone Number")val phoneNumber: Long, @ColumnInfo(name = "Gender")val gender: String){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
