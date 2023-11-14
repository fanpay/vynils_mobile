package com.uniandes.vynilsmobile.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
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
        setSupportActionBar(findViewById(com.uniandes.vynilsmobile.R.id.my_toolbar))
        setupActionBarWithNavController(navController)

        binding.bottomNavigation.setOnItemSelectedListener {
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
                // Limpiar el back stack antes de navegar al fragmento de álbumes
                supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                true
            }
            R.id.artistFragment -> {
                Log.d("Test", "Entre a artist")
                navController.navigate(R.id.artistFragment)
                true
            }
            R.id.page_coleccionistas -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Función no implementada")
                builder.setMessage("Esta función aún no está implementada")
                builder.setPositiveButton("Aceptar") { dialog, _ ->
                    dialog.dismiss()
                }
                val dialog = builder.create()
                dialog.show()
                false
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
}