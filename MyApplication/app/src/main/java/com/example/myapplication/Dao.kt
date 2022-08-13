package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(contact: Contact)

    @Delete
    suspend fun delete(contact: Contact)

    @Query("Select * from contact_table order by Name ASC")
    fun getAllContacts(): LiveData<List<Contact>>
}