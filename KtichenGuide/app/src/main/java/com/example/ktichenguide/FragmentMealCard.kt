package com.example.ktichenguide

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.media.Image
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import org.json.JSONException
import org.json.JSONObject

class FragmentMealCard private constructor(): Fragment() {

    //All the views attached to the layout file of this fragment
    private lateinit var mealCardView : ImageView
    private lateinit var progressBar : ProgressBar
    private lateinit var summaryTextView: TextView

    //Summary string which is to displayed in textView
    private lateinit var summary: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_meal_card, container, false)

        //Attaching the view variables with the layout file
        mealCardView = view.findViewById(R.id.mealCard)
        progressBar = view.findViewById(R.id.progressBar)
        summaryTextView = view.findViewById(R.id.summaryDetails)

        //Fetching the recipe data by its ID
        fetchRecipeDataById()

        //Fetching the Summary of the recipe by its ID
        fetchSummaryDataById()
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    companion object {
        private var recipeId: Int = 0
        fun newInstance(id : Int): FragmentMealCard{
            recipeId = id
            return FragmentMealCard()
        }
    }

    /**
     * This function provides the meal card for the recipe and attaches it to the imageView using Glide
     * library
     */
    private fun fetchRecipeDataById(){
        val recipeUrl = "https://api.spoonacular.com/recipes/$recipeId/card?apiKey=ed0b68fc9042405c806ba66da3488061"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, recipeUrl, null,
            {

                    Glide.with(this.requireContext()).load(it.getString("url")).listener(object : RequestListener<Drawable>{
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = ProgressBar.INVISIBLE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = ProgressBar.INVISIBLE
                            return false
                        }

                    }).into(mealCardView)

            },
            {
                Log.e("Error", it.message.toString())
            })

        MySingleton.getInstance()?.addJsonObjectRequest(this.requireContext(), jsonObjectRequest)

    }

    /**
     * This function fetches the summary of the recipe
     */
    private fun fetchSummaryDataById(){
        val recipeUrl = "https://api.spoonacular.com/recipes/$recipeId/information?apiKey=ed0b68fc9042405c806ba66da3488061"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, recipeUrl, null,
            {
                try {
                    summary = Html.fromHtml(it.getString("summary")).toString()
                    summaryTextView.text = summary
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