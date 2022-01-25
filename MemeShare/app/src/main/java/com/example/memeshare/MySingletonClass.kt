package com.example.memeshare

import android.annotation.SuppressLint
import android.content.Context
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class MySingletonClass {

    private var context : Context? = null
    private constructor(context: Context){
        this.context = context
    }

    companion object{
        @SuppressLint("StaticFieldLeak")
        private var mySingletonClass : MySingletonClass? = null
        fun getInstance(context: Context) : MySingletonClass?{
            if(mySingletonClass == null){
                mySingletonClass = MySingletonClass(context)
            }
            return mySingletonClass
        }
    }

    public fun addRequest(jsonObjectRequest : JsonObjectRequest){
        val queue = Volley.newRequestQueue(context?.applicationContext)
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }

}