package com.example.myapplication

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 1)
abstract class DataBase: RoomDatabase() {
    abstract fun getDao(): Dao

    companion object{
        //Singleton prevents multiple instances of database
        @Volatile
        private var Instance: DataBase? = null

        fun getDataBase(context: Context): DataBase{
            return Instance ?: synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                    DataBase::class.java,
                    "Contact_Database")
                    .build()
                Instance = instance
                instance
            }
        }
    }
}