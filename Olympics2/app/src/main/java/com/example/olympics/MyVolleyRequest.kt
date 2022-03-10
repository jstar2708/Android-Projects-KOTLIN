package com.example.olympics

import android.content.Context
import android.util.Log
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MyVolleyRequest(private val context: Context) {
    companion object{
        private var INSTANCE: MyVolleyRequest? = null

        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: MyVolleyRequest(context).also {
                    INSTANCE = it
                }
            }
    }
    fun addRequest(jsonObjectRequest: JsonObjectRequest){
        Log.e("REQUEST ADDED", "PTA NI KITNE BAAR")
        Volley.newRequestQueue(context).add(jsonObjectRequest)
    }


}