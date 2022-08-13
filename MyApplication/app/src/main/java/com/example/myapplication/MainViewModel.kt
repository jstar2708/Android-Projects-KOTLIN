package com.example.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainViewModel(application: Application): AndroidViewModel(application) {

    private val dataBase = DataBase.getDataBase(application.applicationContext)
    private val dao = dataBase.getDao()
    private val repository: Repository = Repository(dao)
    var allContacts: LiveData<List<Contact>> = repository.getAllContacts()


    fun deleteContact(contact: Contact){
        GlobalScope.launch {
            repository.delete(contact)
        }
    }

    fun insertContact(contact: Contact){
        GlobalScope.launch {
            repository.insert(contact)
        }
    }

}