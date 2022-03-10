package com.example.olympics

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.olympics.ui.news.News
import org.json.JSONObject

class ResponseListener: Response.Listener<JSONObject?>{
    var newsList: MutableLiveData<ArrayList<News>> = MutableLiveData()

    override fun onResponse(response: JSONObject?) {
        val arrayList = ArrayList<News>()
        val articlesArray = response?.getJSONArray("articles")
        if (articlesArray != null) {
            for(i in 0 until articlesArray.length()){
                val newsArticle = articlesArray.getJSONObject(i)
                val news =  News(newsArticle.getString("title")
                    , newsArticle.getString("urlToImage")
                    , newsArticle.getString("url")
                    , newsArticle.getString("publishedAt")
                    , newsArticle.getString("author")
                    , newsArticle.getString("description"))

                arrayList.add(news)
            }
        }
        newsList.value = arrayList
    }

}