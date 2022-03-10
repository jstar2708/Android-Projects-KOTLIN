package com.example.notes

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
// Annotates class to be a Room Database with a table (entity) of the Note class
@Database(entities = arrayOf(Note::class), version = 1, exportSchema = false)
abstract class NoteDataBase: RoomDatabase() {

        abstract fun noteDao(): NotesDao

        companion object {
            // Singleton prevents multiple instances of database opening at the
            // same time.
            @Volatile
            private var INSTANCE: NoteDataBase? = null

            fun getDatabase(context: Context): NoteDataBase {
                // if the INSTANCE is not null, then return it,
                // if it is, then create the database
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDataBase::class.java,
                        "note_database"
                    ).build()
                    INSTANCE = instance
                    // return instance
                    instance
                }
            }
        }
    }
