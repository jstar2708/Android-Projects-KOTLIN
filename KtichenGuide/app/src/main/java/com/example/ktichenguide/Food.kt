package com.example.ktichenguide

data class Food(
    //Title of the recipe
    private val title: String,
    //Image url of the food
    private val imageUrl: String,
    //Source url of the recipe
    private val siteUrl: String,
    //Id of the recipe (spoontacular id for fetching its details)
    private val id: Int,
    //whether the food is vegetarian or not
    private val isVeg: Boolean?) {

    //Returns the title of food
    fun getTitle(): String{
        return title
    }
    //Returns the image url of the food
    fun getImageUrl(): String{
        return imageUrl
    }
    //Returns the source url of the food
    fun getSiteUrl(): String {
        return siteUrl
    }
    //Return the id of food
    fun getId(): Int{
        return id
    }
    //Returns a boolean value of whether food is veg or not
    fun getIsVeg(): Boolean?{
        return isVeg
    }

}