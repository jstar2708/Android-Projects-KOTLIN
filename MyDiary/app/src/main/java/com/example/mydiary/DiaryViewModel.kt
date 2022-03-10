package com.example.mydiary

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DiaryViewModel(application: Application): AndroidViewModel(application) {


    private val database: DiaryDatabase = DiaryDatabase.getDatabase(application)
    private val dao: DiaryDao = database.getDiaryDao()
    private val repository: DiaryRepository = DiaryRepository(dao)
    public val allDiaryNotes = repository.getAllDiaryContent()

    fun insert(diaryContent: DiaryContent) = viewModelScope.launch (Dispatchers.IO){
        repository.insert(diaryContent)
    }

    fun delete(diaryContent: DiaryContent) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(diaryContent)
    }


}