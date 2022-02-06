package com.example.ktichenguide

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FoodAdapter(private val progressBar: ProgressBar): RecyclerView.Adapter<FoodHolder>() {

    private val foodList: ArrayList<Food> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_layout, parent, false)

        val viewHolder = FoodHolder(view)

        view.setOnClickListener {
            val intent = Intent(parent.context, RecipeData::class.java)
            intent.putExtra("id", foodList[viewHolder.adapterPosition].getId())
            parent.context.startActivity(intent)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: FoodHolder, position: Int) {
        progressBar.visibility = ProgressBar.INVISIBLE
        holder.recipeTitleView.text = foodList[position].getTitle()

        Glide.with(holder.itemView.context).load(foodList[position].getImageUrl()).into(holder.recipeImageView)

        //If the recipe is veg then set the veg image
        if(foodList[position].getIsVeg() == true){
            holder.vegView.setImageResource(R.drawable.veg)
        }
        //else set the non-veg image
        else{
            holder.vegView.setImageResource(R.drawable.nonveg)
        }
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    /**
     * This fun is used to update the recycler view items as when the main activity is loaded
     * random recipes are displayed and when user searches for any specific recipe it updates the
     * recycler view items.
     */
    fun updateItems(list: ArrayList<Food>){
        progressBar.visibility = ProgressBar.VISIBLE
        foodList.clear()

        foodList.addAll(list)

        notifyDataSetChanged()
    }

}