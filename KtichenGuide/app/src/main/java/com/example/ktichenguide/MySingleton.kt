package com.example.ktichenguide

import android.content.Context
import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import java.lang.StringBuilder

/**
 * This is a singleton class for adding network requests using Volley library
 */

class MySingleton private constructor() {
    private var queue : RequestQueue? = null
    companion object{

        private var singletonInstance: MySingleton? = null

        public fun getInstance(): MySingleton?{
            if(singletonInstance == null){
                singletonInstance = MySingleton()
            }
            return singletonInstance
        }
    }

    /**
     * This fun adds a JSON Object Request to the queue
     */
    public fun addJsonObjectRequest(context: Context, jsonObjectRequest: JsonObjectRequest){
        if(queue == null){
            queue = Volley.newRequestQueue(context)
        }
        Log.d("", "Object Request Added")
        queue?.add(jsonObjectRequest)
    }

    /**
     * This fun adds a JSON Array Request to the queue
     */
    public fun addJsonArrayRequest(context: Context, jsonArrayRequest: JsonArrayRequest, query: String){
        if(queue == null){
            queue = Volley.newRequestQueue(context)
        }
        Log.d("", "Array Request Added $query")
        queue?.add(jsonArrayRequest)
    }

}