package com.example.olympics

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.olympics.ui.news.News
import org.json.JSONObject

class Repository(private val context: Context) {

    private lateinit var newsList: MutableLiveData<ArrayList<News>>

    private val responseListener: ResponseListener = ResponseListener()

    fun getNewsData(): MutableLiveData<ArrayList<News>>{
        fetchOlympicNews()
        newsList = responseListener.newsList
        return newsList
    }

    private fun fetchOlympicNews(){
        val url = "https://newsapi.org/v2/everything?q=olympics&from=2022-02-18&sortBy=publishedAt&apiKey=6ebe37b03a944b6389e6db2b16d57465"
        val jsonObjectRequest = object: JsonObjectRequest(Request.Method.GET, url, null, responseListener,

             {
            Log.e("ERROR", it.message.toString())
        }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String>? {
                val headers = HashMap<String, String>()
                //headers.put("Content-Type", "application/json");
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }
        MyVolleyRequest.getInstance(context).addRequest(jsonObjectRequest)
    }

}