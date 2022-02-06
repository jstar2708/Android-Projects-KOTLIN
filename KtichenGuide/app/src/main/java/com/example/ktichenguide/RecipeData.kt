package com.example.ktichenguide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class RecipeData : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_data)

        //Receiving the id from main activity
        val intent = intent
        val id = intent.getIntExtra("id", 0)

        //Attaching the view variables
        val tabLayout: TabLayout = findViewById(R.id.tabLayoutRecipeData)
        val viewPager: ViewPager = findViewById(R.id.viewPagerRecipeData)


        //initializing the page adapter for view pager
        val viewPagerAdapter = PageAdapter(supportFragmentManager, id)

        viewPager.adapter = viewPagerAdapter

        //All the fragments will remain in the memory
        viewPager.offscreenPageLimit = 3

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    viewPager.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
        })

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

    }


}