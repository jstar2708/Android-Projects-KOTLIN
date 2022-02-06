package com.example.ktichenguide


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Page adapter for the view pager to control the loading of the fragments
 */
class PageAdapter(fragmentManager: FragmentManager, private val id: Int): FragmentPagerAdapter(fragmentManager) {

    private val fragmentMealCard: FragmentMealCard = FragmentMealCard.newInstance(id)
    private val fragmentNutrients: FragmentNutrients = FragmentNutrients.newInstance(id)
    private val fragmentRecipeInfo: FragmentRecipeInfo = FragmentRecipeInfo.newInstance(id)

    private val tabCount = 3
    override fun getCount(): Int {
        return tabCount
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0-> fragmentMealCard
            1-> fragmentRecipeInfo
            else-> fragmentNutrients
        }
    }

}