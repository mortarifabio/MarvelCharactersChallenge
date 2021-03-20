package com.mortarifabio.marvelcharacterschallenge.home.view

import android.content.res.Configuration
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.mortarifabio.marvelcharacterschallenge.R
import com.mortarifabio.marvelcharacterschallenge.databinding.ActivityHomeBinding
import com.mortarifabio.marvelcharacterschallenge.utils.NetworkUtils

class HomeActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }
    private val networkUtils by lazy {
        NetworkUtils(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupActionBar(resources.configuration)
        setupNavigation()
        setupNetworkObserver()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setupActionBar(newConfig)
    }

    private fun setupActionBar(configuration: Configuration) {
        when(configuration.orientation){
            ORIENTATION_LANDSCAPE -> supportActionBar?.hide()
            else -> supportActionBar?.show()
        }
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fcvHomeContainer) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bnHomeNavigation, navController)
    }

    private fun setupNetworkObserver() {
        networkUtils.apply {
            showNoConnectionAlert(!isNetworkConnected())
            setupNetworkObserver { isConnected ->
                showNoConnectionAlert(!isConnected)
            }
        }
    }

    private fun showNoConnectionAlert(show: Boolean){
        runOnUiThread {
            if(show){
                binding.tvHomeInternetAlert.visibility = VISIBLE
            } else {
                binding.tvHomeInternetAlert.visibility = GONE
            }
        }
    }
}