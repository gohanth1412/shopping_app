package com.example.shoppingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.shoppingapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(2000L)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.botNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }

                R.id.exploreFragment -> {
                    navController.navigate(R.id.exploreFragment)
                    true
                }

                R.id.cartFragment -> {
                    navController.navigate(R.id.cartFragment)
                    true
                }

                R.id.favouriteFragment -> {
                    navController.navigate(R.id.favouriteFragment)
                    true
                }

                R.id.accountFragment -> {
                    navController.navigate(R.id.accountFragment)
                    true
                }

                else -> false
            }
        }

        //binding.botNav.setupWithNavController(navController)
    }

    fun showBotNav() {
        binding.botNav.visibility = View.VISIBLE
    }

    fun hideBotNav() {
        binding.botNav.visibility = View.GONE
    }
}