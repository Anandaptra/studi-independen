package com.rogo.final_project.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rogo.final_project.R
import com.rogo.final_project.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class dMainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragContainer) as NavHostFragment

        navController = navHostFragment.navController
        navController = navHostFragment!!.findNavController()

        navController.addOnDestinationChangedListener{ _, destination, _ ->
            when(destination.id){
                R.id.splashFragment,R.id.loginFragment -> {
                    binding.bottomNav.visibility = View.GONE
                } R.id.homeFragment2,R.id.historyNonLoginFragment2 ,R.id.notifFragment2, R.id.akunNonLoginFragment2 -> {
                binding.bottomNav.visibility = View.VISIBLE
            } else -> {
                binding.bottomNav.visibility = View.VISIBLE
            }
            }
        }

        binding.bottomNav.setupWithNavController(navController)

        binding.bottomNav.setOnClickListener { item ->
            when(item.id) {
                R.id.homeFragment2 -> {
                    navController.navigate(R.id.homeFragment2)
                    true
                }
                R.id.historyNonLoginFragment2 -> {
                    navController.navigate(R.id.historyNonLoginFragment2)
                    true
                }
                R.id.notifFragment2 -> {
                    navController.navigate(R.id.notifFragment2)
                    true
                }
                R.id.akunNonLoginFragment2 -> {
                    navController.navigate(R.id.akunNonLoginFragment2)
                    true
                }
            }
        }


    }

//        val appBarConfiguration = AppBarConfiguration.Builder(
//            R.id.homeFragment2,
//            R.id.historyNonLoginFragment2,
//            R.id.notifFragment2,
//            R.id.akunNonLoginFragment2
//        ).build()

//        setupActionBarWithNavController(navController,appBarConfiguration)
//        bottomNav.setupWithNavController(navController)
//        navController.addOnDestinationChangedListener{_,item,_ ->
//            when(item.id){
//                R.id.homeFragment2 -> hideBottom(false)
//                R.id.historyNonLoginFragment2 -> hideBottom(false)
//                R.id.notifFragment2 -> hideBottom(false)
//                R.id.akunNonLoginFragment2 -> hideBottom(false)
//                else -> hideBottom(true)
//            }
//        }
}

//    private fun hideBottom(hide: Boolean) {
//        if (hide){
//            bottomNav.visibility = View.GONE
//        }else{
//            bottomNav.visibility = View.VISIBLE
//        }
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        return navController.navigateUp() || super.onSupportNavigateUp()
//    }
