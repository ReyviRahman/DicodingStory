package com.rey.dicodingstory.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rey.dicodingstory.R
import com.rey.dicodingstory.data.Result
import com.rey.dicodingstory.data.pref.UserModel
import com.rey.dicodingstory.databinding.ActivityLoginBinding
import com.rey.dicodingstory.databinding.ActivityMainBinding
import com.rey.dicodingstory.ui.ViewModelFactory
import com.rey.dicodingstory.ui.main.MainActivity

class LoginActivity : AppCompatActivity() {
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val pwd = binding.etPwd.text.toString()
            viewModel.postUserLogin(email, pwd).observe(this) { result ->
                if (result != null) {
                    when(result){
                        Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            viewModel.saveSession(UserModel(email, result.data.loginResult?.token.toString()))
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                            binding.progressBar.visibility = View.GONE
                        }
                        is Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}