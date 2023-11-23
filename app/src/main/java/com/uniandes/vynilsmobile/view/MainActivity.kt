package com.uniandes.vynilsmobile.view

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.uniandes.vynilsmobile.R
import com.uniandes.vynilsmobile.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the navigation host fragment from this Activity
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        // Instantiate the navController using the NavHostFragment
        navController = navHostFragment.navController

        // Make sure actions in the ActionBar get propagated to the NavController
        setSupportActionBar(findViewById(R.id.my_toolbar))
        setupActionBarWithNavController(navController)

        binding.bottomNavigation.setupWithNavController(navController)
        binding.bottomNavigation.setOnItemSelectedListener {
            NavigationUI.onNavDestinationSelected(it, navController)
            handleBottomNavigation(
                it.itemId
            )
        }
        binding.bottomNavigation.selectedItemId = R.id.albumFragment

        setContentView(binding.root)
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun handleBottomNavigation(menuItemId: Int): Boolean {
        return when (menuItemId) {
            R.id.albumFragment -> {
                navController.popBackStack()
                navController.navigate(R.id.albumFragment)
                true
            }
            R.id.artistFragment -> {
                navController.popBackStack()
                navController.navigate(R.id.artistFragment)
                true
            }
            R.id.collectorsFragment -> {
                navController.popBackStack()
                navController.navigate(R.id.collectorsFragment)
                true
            }
            // Handle other menu items similarly
            else -> false
        }
    }

    fun showErrorLayout(show: Boolean, text: String) {
        val splashErrorLayout: ConstraintLayout = findViewById(R.id.splash_error_layout)
        val splashErrorLayoutErrorText: TextView = findViewById(R.id.textViewError)

        if (show) {
            splashErrorLayout.visibility = View.VISIBLE
            splashErrorLayoutErrorText.text = text
        } else {
            splashErrorLayout.visibility = View.GONE
            splashErrorLayoutErrorText.text = text
        }
    }

    fun showNotDataFoundLayout(show: Boolean, text: String) {
        val splashNotDataFoundLayout: ConstraintLayout = findViewById(R.id.splash_no_data_found_layout)
        val splashNotDataFoundText: TextView = findViewById(R.id.textViewNotFoundData)

        if (show) {
            splashNotDataFoundLayout.visibility = View.VISIBLE
            splashNotDataFoundText.text = text
        } else {
            splashNotDataFoundLayout.visibility = View.GONE
            splashNotDataFoundText.text = text
        }
    }
}