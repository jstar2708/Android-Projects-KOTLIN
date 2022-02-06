package com.example.ktichenguide

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONException
import java.lang.StringBuilder

class FragmentRecipeInfo private constructor() : Fragment() {

    //View related to this fragment's layout file
    private lateinit var vegetarianAns: ImageView
    private lateinit var veganAns: ImageView
    private lateinit var glutenFreeAns: ImageView
    private lateinit var dairyFreeAns: ImageView
    private lateinit var healthScore: TextView
    private lateinit var likesAns: TextView
    private lateinit var ingredientsView: TextView
    private lateinit var sourceUrl: String
    private lateinit var ingrediants: String
    private lateinit var recipeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_recipe_info, container, false)

        //Attaching the views
        veganAns = view.findViewById(R.id.veganAns)
        vegetarianAns = view.findViewById(R.id.vegetarianAns)
        glutenFreeAns = view.findViewById(R.id.glutenFreeAns)
        dairyFreeAns = view.findViewById(R.id.dairyFreeAns)
        healthScore = view.findViewById(R.id.scoreAns)
        likesAns = view.findViewById(R.id.likesAns)
        ingredientsView = view.findViewById(R.id.ingredientsData)
        recipeButton = view.findViewById(R.id.recipeButton)
        val linearLayout: LinearLayout = view.findViewById(R.id.linear_layoutRecipe)

        //Fetching the recipe details by its ID
        fetchRecipeDataById()

        //setting on click listener for the recipeButton
        recipeButton.setOnClickListener {
            //Creating a custom chrome tab
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(this.requireContext(), Uri.parse(sourceUrl))
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        private var recipeId: Int = 0
        fun newInstance(id: Int) : FragmentRecipeInfo{
            recipeId = id
            return FragmentRecipeInfo()
        }
    }

    /**
     * This fun fetches recipe data by its ID
     */
    private fun fetchRecipeDataById(){
        val recipeUrl = "https://api.spoonacular.com/recipes/${recipeId}/information?apiKey=ed0b68fc9042405c806ba66da3488061&includeNutrition=true"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, recipeUrl, null,
            {

                try {
                    if(!it.getBoolean("vegetarian")){
                        vegetarianAns.setImageResource(R.drawable.nonveg)
                    }
                    if(!it.getBoolean("vegan")){
                        veganAns.setImageResource(R.drawable.cross)
                    }
                    if(!it.getBoolean("glutenFree")){
                        glutenFreeAns.setImageResource(R.drawable.cross)
                    }
                    if(!it.getBoolean("dairyFree")){
                        dairyFreeAns.setImageResource(R.drawable.cross)
                    }
                    healthScore.text = it.getDouble("healthScore").toInt().toString()
                    likesAns.text = it.getInt("aggregateLikes").toString()

                    val ingredientsArray = it.getJSONArray("extendedIngredients")
                    val stringBuilder: StringBuilder = StringBuilder()
                    for(i in 0 until ingredientsArray.length()-1){
                        stringBuilder.append("► " + ingredientsArray.getJSONObject(i).getString("original") + "\n")
                    }
                    stringBuilder.append("► " + ingredientsArray.getJSONObject(ingredientsArray.length()-1).getString("original") + ".")
                    ingrediants = stringBuilder.toString()
                    ingredientsView.text = ingrediants

                    sourceUrl = it.getString("sourceUrl")
                }
                catch (e: JSONException){
                    Log.e("Error", "Problem in parsing json results")
                }
            }, {
                Log.e("Error", it.message.toString())
            })

        MySingleton.getInstance()?.addJsonObjectRequest(this.requireContext(), jsonObjectRequest)

    }
}