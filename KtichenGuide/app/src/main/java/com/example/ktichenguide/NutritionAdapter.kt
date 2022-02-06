package com.example.ktichenguide

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView

class NutritionAdapter(): RecyclerView.Adapter<NutritionViewHolder>() {
    private lateinit var progressBar: ProgressBar
    private var nutritionList: ArrayList<Nutrients> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NutritionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.nutrients_recycler_layout, parent, false)

        //Setting the progress bar invisible
        progressBar.visibility = ProgressBar.INVISIBLE
        return NutritionViewHolder(view)
    }

    override fun onBindViewHolder(holder: NutritionViewHolder, position: Int) {
        //Setting the data to their respective views
        holder.nutritionName.text = nutritionList[position].getNutrientName()
        holder.nutritionQuantity.text = nutritionList[position].getNutrientQuantity()
        holder.nutritionUnit.text = nutritionList[position].getNutrientUnit()
    }

    override fun getItemCount(): Int {
        return nutritionList.size
    }

    /**
     * This fun updates the list items
     */
    fun updateItems(list: ArrayList<Nutrients>, progressbarLocal: ProgressBar){
        progressBar = progressbarLocal
        nutritionList.clear()

        nutritionList.addAll(list)

        notifyDataSetChanged()
    }
}