package com.example.myapplication

import androidx.lifecycle.LiveData

class Repository(private val dao: Dao) {

    suspend fun insert(contact: Contact){
        dao.insert(contact)
    }

    suspend fun delete(contact: Contact){
        dao.delete(contact)
    }

    fun getAllContacts(): LiveData<List<Contact>>{
        return dao.getAllContacts()
    }
}