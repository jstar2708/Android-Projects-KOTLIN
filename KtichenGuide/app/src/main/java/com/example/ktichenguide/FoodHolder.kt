package com.example.ktichenguide

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    //ImageView for holding the food image
    val recipeImageView: ImageView = itemView.findViewById(R.id.recipeImage)
    //TextView for holding the title of the food
    val recipeTitleView: TextView = itemView.findViewById(R.id.recipeName)
    //ImageView for holding the isVeg value of the food
    val vegView: ImageView = itemView.findViewById(R.id.vegView)
}