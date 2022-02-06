package com.example.ktichenguide

data class Nutrients(

    //Nutrient name
    private val nutrientName: String,
    //Nutrient quantity
    private val nutrientQuantity: String,
    //Unit of the nutrient
    private val nutrientUnit: String
) {

    //Returns the nutrient name
    fun getNutrientName(): String{
        return nutrientName
    }
    //Returns the nutrient quantity
    fun getNutrientQuantity(): String{
        return nutrientQuantity
    }
    //Returns the nutrient unit
    fun getNutrientUnit(): String{
        return nutrientUnit
    }
}