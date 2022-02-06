package com.example.ktichenguide

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONException


class FragmentNutrients private constructor() : Fragment() {

    //Adapter for recycler view
    private lateinit var nutrientAdapter: NutritionAdapter

    //Array of Nutrients
    private val nutrientList: ArrayList<Nutrients> = ArrayList()

    //Progress bar in the layout file
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_nutrients, container, false)

        //Attaching the progress bar
        progressBar = view.findViewById(R.id.progressBarNutrients)

        //Fetching the nutrients info from the api
        fetchRecipeDataById()

        //Initializing the adapter
        nutrientAdapter = NutritionAdapter()

        //Attaching the recycler view
        val nutritionRecyclerView: RecyclerView = view.findViewById(R.id.nutrientsRecyclerView)

        nutritionRecyclerView.layoutManager = LinearLayoutManager(this.requireContext())

        nutritionRecyclerView.adapter = nutrientAdapter

        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    companion object {
        private var recipeId = 0
        fun newInstance(id: Int): FragmentNutrients{
            recipeId = id
            return FragmentNutrients()
        }
    }


    /**
     * This fun fetches the nutrients info from the api
     */
    private fun fetchRecipeDataById(){

        val recipeUrl = "https://api.spoonacular.com/recipes/$recipeId/information?apiKey=ed0b68fc9042405c806ba66da3488061&includeNutrition=true"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, recipeUrl, null,
            {
                try {
                    val nutrientsObject = it.getJSONObject("nutrition")
                    val nutrientsJsonArray = nutrientsObject.getJSONArray("nutrients")
                    for(i in 0 until nutrientsJsonArray.length()){
                        val nutrientName = nutrientsJsonArray.getJSONObject(i).getString("name")
                        val nutrientQuantity = nutrientsJsonArray.getJSONObject(i).getDouble("amount").toString()
                        val nutrientUnit = nutrientsJsonArray.getJSONObject(i).getString("unit")
                        nutrientList.add(Nutrients(nutrientName, nutrientQuantity, nutrientUnit).copy())
                    }
                }
                catch (e: JSONException){
                    Log.e("Error", "Problem in parsing Json Results")
                }
                nutrientAdapter.updateItems(nutrientList, progressBar)

            }, {
                Log.e("Error", it.message.toString() + "Hello" + recipeId.toString())
            })

        MySingleton.getInstance()?.addJsonObjectRequest(this.requireContext(), jsonObjectRequest)

    }


}