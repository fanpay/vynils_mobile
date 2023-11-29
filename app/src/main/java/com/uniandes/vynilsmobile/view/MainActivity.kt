package com.uniandes.vynilsmobile.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.uniandes.vynilsmobile.R
import com.uniandes.vynilsmobile.databinding.ActivityMainBinding
import java.util.Locale


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var menu: Menu
    private val prefsName = "MyPrefsFile"

    private val selectedLanguageKey = "selected_language"
    private val selectedIconKey = "selected_icon"

    private val defaultLanguage = "es"
    private val defaultIconResId = R.drawable.ic_flag_es

    private fun getSharedPreferences() = getSharedPreferences(prefsName, 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtener las preferencias compartidas
        val prefs = getSharedPreferences()
        val defaultLanguage = prefs.getString(selectedLanguageKey, defaultLanguage) ?: defaultLanguage

        // Establecer el idioma por defecto
        setLocale(defaultLanguage)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        this.menu = menu

        val prefs = getSharedPreferences()
        val selectedIconResId = prefs.getInt(selectedIconKey, defaultIconResId)

        updateLanguageIcon(selectedIconResId)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.itemId

        if (id == R.id.buttonLanguage) {
            val languages = arrayOf("English", "Español")

            val langSelectorBuilder = AlertDialog.Builder(this@MainActivity)
            langSelectorBuilder.setTitle("Choose language:")
            langSelectorBuilder.setSingleChoiceItems(languages, -1) { dialog, selection ->
                when(selection) {
                    0 -> {
                        val newLocale = Locale("en")
                        val configuration = resources.configuration
                        configuration.setLocale(newLocale)
                        configuration.setLayoutDirection(newLocale)

                        val context = createConfigurationContext(configuration)
                        context.resources

                        updateLanguageIcon(R.drawable.ic_flag_us)
                        saveLanguageSettings("en", R.drawable.ic_flag_us)
                    }
                    1 -> {
                        val newLocale = Locale(defaultLanguage)
                        val configuration = resources.configuration
                        configuration.setLocale(newLocale)
                        configuration.setLayoutDirection(newLocale)

                        val context = createConfigurationContext(configuration)
                        context.resources

                        updateLanguageIcon(R.drawable.ic_flag_es)
                        saveLanguageSettings("es", R.drawable.ic_flag_es)
                    }
                }
                dialog.dismiss()
                recreate()
            }
            langSelectorBuilder.create().show()

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun updateLanguageIcon(iconResId: Int) {
        val menuItem = menu.findItem(R.id.buttonLanguage)
        menuItem.setIcon(iconResId)
    }

    private fun saveLanguageSettings(language: String, iconResId: Int) {
        val editor = getSharedPreferences().edit()
        editor.putString(selectedLanguageKey, language)
        editor.putInt(selectedIconKey, iconResId)
        editor.apply()
    }

    private fun setLocale(language: String) {
        val locale = Locale(language)
        val configuration = resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        createConfigurationContext(configuration)
        resources.updateConfiguration(configuration, resources.displayMetrics)
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