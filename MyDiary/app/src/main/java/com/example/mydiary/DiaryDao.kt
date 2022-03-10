package com.example.mydiary

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DiaryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(diaryContent: DiaryContent)

    @Delete
    suspend fun delete(diaryContent: DiaryContent)

    @Query("Select * from my_diary order by date DESC")
    fun getAllDiaryContent(): LiveData<List<DiaryContent>>

}