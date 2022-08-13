package com.example.firstchat2

import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.firstchat2.databinding.ActivityNavigationBinding
import com.google.firebase.auth.FirebaseAuth

class NavigationActivity : AppCompatActivity(){

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityNavigationBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        setSupportActionBar(binding.appBarNavigation.toolbar)

        binding.appBarNavigation.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_navigation)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navChats, R.id.navGlance, R.id.navCalls, R.id.navSettings, R.id.navLogOut
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        Glide.with(this).load(auth.currentUser?.photoUrl).placeholder(R.drawable.woman).into(navView.getHeaderView(0).findViewById<ImageView>(R.id.navImageView))
        navView.getHeaderView(0).findViewById<TextView>(R.id.navName).text = auth.currentUser?.displayName
        navView.getHeaderView(0).findViewById<TextView>(R.id.navEmail).text = auth.currentUser?.email

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.navigation, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_navigation)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}