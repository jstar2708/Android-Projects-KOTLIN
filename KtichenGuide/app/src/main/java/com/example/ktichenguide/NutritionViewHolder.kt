package com.example.ktichenguide

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NutritionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    //TextView to hold nutrient name
    val nutritionName: TextView = itemView.findViewById(R.id.nutrientName)

    //TextView to hold nutrient quantity
    val nutritionQuantity: TextView = itemView.findViewById(R.id.nutrientQuantity)

    //TextView to hold nutrient unit
    val nutritionUnit: TextView = itemView.findViewById(R.id.nutrientUnit)
}