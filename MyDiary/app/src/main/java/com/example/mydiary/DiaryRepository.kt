package com.example.mydiary

import androidx.lifecycle.LiveData

class DiaryRepository(private val diaryDao: DiaryDao) {

    suspend fun insert(diaryContent: DiaryContent){
        diaryDao.insert(diaryContent)
    }

    suspend fun delete(diaryContent: DiaryContent){
        diaryDao.delete(diaryContent)
    }

    fun getAllDiaryContent(): LiveData<List<DiaryContent>>{
        return diaryDao.getAllDiaryContent()
    }
}
