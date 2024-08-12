package com.rey.dicodingstory.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.rey.dicodingstory.R
import com.rey.dicodingstory.databinding.ActivityMainBinding
import com.rey.dicodingstory.ui.ViewModelFactory
import com.rey.dicodingstory.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                val navController = findNavController(R.id.nav_host_fragment_activity_main)

                val appBarConfiguration = AppBarConfiguration(
                    setOf(
                        R.id.navigation_home, R.id.navigation_upload, R.id.navigation_map
                    )
                )

                setupActionBarWithNavController(navController, appBarConfiguration)
                binding.bottomNav.setupWithNavController(navController)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}