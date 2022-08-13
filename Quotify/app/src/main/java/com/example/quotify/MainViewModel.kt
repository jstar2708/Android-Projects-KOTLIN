package com.example.quotify

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import java.nio.charset.Charset

class MainViewModel(private val context: Context): ViewModel() {

    private var quotes: Array<Quote> = emptyArray()
    var index = 0
    var size = 0

    init{
        quotes = loadQuotesFromJSON()
        size = quotes.size
    }

    private fun loadQuotesFromJSON(): Array<Quote>{
        val inputStream = context.assets.open("quotes.json")
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val json = String(buffer, Charsets.UTF_8)
        val gson = Gson()
        return gson.fromJson(json, Array<Quote>::class.java)
    }

    fun getQuote(): Quote = quotes[index]

    fun nextQuote(): Quote = quotes[++index]

    fun previousQuote() = quotes[--index]
}