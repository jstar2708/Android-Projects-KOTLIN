package com.example.ktichenguide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONArray
import org.json.JSONException
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    //Adapter for displaying the food Items on the main activity
    private lateinit var mAdapter: FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Attaching the view with the variables
        val searchEditTextView: EditText = findViewById(R.id.searchEditView)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val searchImageButton = findViewById<ImageButton>(R.id.search_button)
        val progressBar = findViewById<ProgressBar>(R.id.progressBarMainActivity)

        //Setting the progress bar visible on screen
        progressBar.visibility = ProgressBar.VISIBLE

        //On click listener for the search button
        searchImageButton.setOnClickListener {
            val query = makeQuery(searchEditTextView.text.toString())
            fetchSearchedFood(query)
        }

        //Layout manager for recycler view
        recyclerView.layoutManager = LinearLayoutManager(this)

        //API call function for getting random recipe details
        fetchRandomFood()

        //initializing the adapter
        mAdapter = FoodAdapter(progressBar)

        //Attaching the adapter to the recycler view
        recyclerView.adapter = mAdapter
    }

    /**
     * This fun is used to generate proper query so that it can be added in the url
     * Ex-> It converts italian pasta to italian+pasta
     */
    private fun makeQuery(query: String) : String{
        val str = StringBuilder()
        var i = 0
        var j = 0
        while(i < query.length){
            if(query[i] == ' '){
                str.append(query.substring(j, i))
                j = i+1
                str.append('+')
            }
            i++
        }
        str.append(query.substring(j, i))
        return str.toString()
    }

    /**
     * Fun to get list of random recipe details
     */
    private fun fetchRandomFood() {
        val foodList: ArrayList<Food> = ArrayList()
        val randomFoodUrl =
            "https://api.spoonacular.com/recipes/random?apiKey=ed0b68fc9042405c806ba66da3488061&number=5"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, randomFoodUrl, null,
            {
                try {
                    val recipes: JSONArray = it.getJSONArray("recipes")
                    for (i in 0 until recipes.length()) {
                        val title = recipes.getJSONObject(i).getString("title")
                        val imageUrl = recipes.getJSONObject(i).getString("image")
                        val siteUrl = recipes.getJSONObject(i).getString("sourceUrl")
                        val isVeg = recipes.getJSONObject(i).getBoolean("vegetarian")
                        val id = recipes.getJSONObject(i).getInt("id")
                        foodList.add(Food(title, imageUrl, siteUrl, id, isVeg))
                    }

                    mAdapter.updateItems(foodList)
                }
                catch (e: JSONException){
                    Log.e("Error", "Error in parsing json result")
                    fetchRandomFood()
                }
            },
            {
                Log.e("", "Error occurred + ${it.networkResponse}")
            })

        MySingleton.getInstance()?.addJsonObjectRequest(this, jsonObjectRequest)
    }

    /**
     * This fun is used to get details regarding the searched recipe in the editText view
     * It provides a list of 10 Food items
     */
    private fun fetchSearchedFood(query: String) {
        val searchFoodUrl = "https://api.spoonacular.com/recipes/autocomplete?apiKey=ed0b68fc9042405c806ba66da3488061&query=${query}&number=10"

        val foodList: ArrayList<Food> = ArrayList()

        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, searchFoodUrl, null,
            {

                try {
                    for(i in 0 until it.length()){
                        val title = it.getJSONObject(i).getString("title")
                        val id = it.getJSONObject(i).getInt("id")
                        val imageUrl = "https://spoonacular.com/recipeImages/${id}-556x370.jpg"
                        foodList.add(Food(title, imageUrl, "", id, null))
                    }
                }
                catch (e: JSONException){
                    Log.e("Error", "Problem in parsing Json results")
                }

                mAdapter.updateItems(foodList)
            },{
                Log.e("", "Error occurred ${it.networkResponse}")
            })

        MySingleton.getInstance()?.addJsonArrayRequest(this, jsonArrayRequest, query)

    }
}
