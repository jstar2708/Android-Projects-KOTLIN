package com.example.olympics.ui.news

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.example.olympics.MyVolleyRequest
import com.example.olympics.Repository

class NewsViewModel(application: Application): AndroidViewModel(application) {

    private val repository: Repository = Repository(application.applicationContext)
    var newsList: MutableLiveData<ArrayList<News>> = repository.getNewsData()


}