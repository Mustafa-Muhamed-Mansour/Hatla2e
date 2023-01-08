package com.lafa.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.lafa.R
import com.lafa.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity()
{

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        initViews()
        clickedView()

    }

    private fun initViews() {
        navController = Navigation.findNavController(this, R.id.nav_host)
//        setupWithNavController(binding.navView, navController)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.splashFragment, R.id.loginFragment, R.id.registerFragment, R.id.questionFragment, R.id.productDetailFragment, R.id.profileFragment, R.id.complaintFragment -> binding.navView.visibility = View.GONE
                else -> binding.navView.visibility = View.VISIBLE
            }
        }
    }

    private fun clickedView() {
        binding.navView.setItemSelected(R.id.homeFragment, true)
        binding.navView.setOnItemSelectedListener {
            when (it) {
                R.id.homeFragment -> navController.navigate(R.id.homeFragment)
                R.id.settingFragment -> navController.navigate(R.id.settingFragment)
                R.id.searchFragment -> navController.navigate(R.id.searchFragment)
                R.id.favoriteFragment -> navController.navigate(R.id.favoriteFragment)
                else -> Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}