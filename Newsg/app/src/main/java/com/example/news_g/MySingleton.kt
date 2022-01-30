package com.example.news_g

import android.content.Context
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import java.security.AccessControlContext

class MySingleton private constructor() {

    companion object{
        private var singleInstance : MySingleton? = null
        fun getInstance() : MySingleton?{
            if(singleInstance == null){
                singleInstance = MySingleton()
            }
            return singleInstance
        }
    }

    fun addNetworkRequest(context: Context, jsonObjectRequest: JsonObjectRequest){
        val queue = Volley.newRequestQueue(context.applicationContext)
        queue.add(jsonObjectRequest)
    }

}