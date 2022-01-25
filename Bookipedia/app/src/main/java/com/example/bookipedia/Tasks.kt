package com.example.bookipedia

import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.nio.charset.Charset
import javax.net.ssl.HttpsURLConnection

class Tasks {

    private var URL_BOOKS: String = "https://www.googleapis.com/books/v1/volumes?q="

    fun makeHttpRequest(topic : String?) : ArrayList<Book>{
        var url : URL? = createUrl(topic)
        var jsonResponse : String = ""
        if(url == null){
            return ArrayList()
        }
        val httpsURLConnection : HttpsURLConnection
        var inputStream : InputStream? = null
        try {
            httpsURLConnection = url.openConnection() as HttpsURLConnection
            httpsURLConnection.requestMethod = "GET"
            httpsURLConnection.readTimeout = 1500
            httpsURLConnection.connectTimeout = 1000
            httpsURLConnection.connect()

                if(httpsURLConnection.responseCode == 200){
                    jsonResponse = readFromInputStream(httpsURLConnection.inputStream)
                }
                else{
                    Log.e("", httpsURLConnection.responseMessage + httpsURLConnection.responseCode.toString())
                }
        }
        catch (e : IOException){
            Log.e("", "Problem in getting response from server", e)
        }

        return extractFromJson(jsonResponse)
    }

    private fun createUrl(topic: String?) : URL? {
        var Url : URL? = null
        try {
            Url = URL("$URL_BOOKS$topic&maxResults=10")
        }
        catch (e : MalformedURLException){
            Log.e("", "Malformed Url + $e")
        }
        return Url
    }

    private fun readFromInputStream(inputStream: InputStream?) : String{
        var stringBuilder : StringBuilder = StringBuilder()
        if(inputStream == null){
            return ""
        }

        val inputStreamReader : InputStreamReader = InputStreamReader(inputStream, Charset.forName("UTF-8"))
        val bufferedReader : BufferedReader = BufferedReader(inputStreamReader)

        var line : String? = bufferedReader.readLine()
        while(line != null){
            stringBuilder.append(line)
            line = bufferedReader.readLine()
        }

        return stringBuilder.toString()
    }

    private fun extractFromJson(jsonResponse : String?) : ArrayList<Book>{
        val booklist : ArrayList<Book> = ArrayList()

        if(jsonResponse == null){
            return booklist
        }

        try {
            val rootObject : JSONObject = JSONObject(jsonResponse)
            val rootElement : JSONArray = rootObject.getJSONArray("items")
            var j = 0
            while(j < rootElement.length()){

                val itemElement : JSONObject = rootElement.getJSONObject(j)
                val volumeInfo : JSONObject = itemElement.getJSONObject("volumeInfo")
                val title : String = volumeInfo.getString("title")
                val authors : StringBuilder = StringBuilder()
                try {
                    val authorList : JSONArray = volumeInfo.getJSONArray("authors")
                    var i = 0
                    while(i < authorList.length()){
                        if(i == authorList.length()-1){
                            authors.append(authorList.get(i))
                        }
                        else{
                            authors.append(authorList.get(i))
                            authors.append(", ")
                        }
                        i++;
                    }
                }
                catch (e : JSONException){
                    Log.e("", "No authors found", e)
                }

                if(authors.toString() == ""){
                    authors.append("No authors found")
                }
                booklist.add(Book(title, authors.toString()))
                j++;
            }
        }
        catch (e : JSONException){

            Log.e("", "Problem in parsing json results", e)
        }
        return booklist
    }
}