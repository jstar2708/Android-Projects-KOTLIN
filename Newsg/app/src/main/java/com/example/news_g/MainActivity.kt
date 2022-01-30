package com.example.news_g

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.request.RequestListener
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() , NewsOnClicked{

    private lateinit var mAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchData()

        mAdapter = NewsAdapter(this)

        recyclerView.adapter = mAdapter
    }

    private fun fetchData(){

        val newsList : ArrayList<News> = ArrayList()

        val url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=6ebe37b03a944b6389e6db2b16d57465"

        val jsonObjectRequest = object: JsonObjectRequest(Request.Method.GET, url, null,
            { response: JSONObject->

                val articleArray = response.getJSONArray("articles")
                for(i in 0 until articleArray.length()) {
                    val title: String = articleArray.getJSONObject(i).getString("title")
                    val author: String = articleArray.getJSONObject(i).getString("author")
                    val urlO: String = articleArray.getJSONObject(i).getString("url")
                    val urlImage: String = articleArray.getJSONObject(i).getString("urlToImage")
                    newsList.add(News(author, urlImage, title, urlO))
                }

                mAdapter.updateNews(newsList)
            }, Response.ErrorListener{
                Log.d("", it.networkResponse.statusCode.toString())
            }
        )
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }

        val instance: MySingleton? = MySingleton.getInstance()
        instance?.addNetworkRequest(this, jsonObjectRequest)
    }

    override fun onClick(item: News) {
        val builder = CustomTabsIntent.Builder();
        val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(item.url));
    }

}