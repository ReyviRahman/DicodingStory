package com.rey.dicodingstory.ui.register

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
import com.rey.dicodingstory.databinding.ActivityRegisterBinding
import com.rey.dicodingstory.ui.ViewModelFactory
import com.rey.dicodingstory.ui.login.LoginActivity
import com.rey.dicodingstory.ui.main.MainActivity

class RegisterActivity : AppCompatActivity() {
    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        binding.btnCreateAccount.setOnClickListener {
            val name = binding.etNama.text.toString()
            val email = binding.etEmail.text.toString()
            val pwd = binding.etPwd.text.toString()
            viewModel.postUserRegister(name, email, pwd).observe(this) { result ->
                when(result) {
                    Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        viewModel.postUserLogin(email, pwd).observe(this) { resultLogin ->
                            when(resultLogin) {
                                Result.Loading -> {

                                }
                                is Result.Success -> {
                                    binding.progressBar.visibility = View.GONE
                                    val token = resultLogin.data.loginResult?.token.toString()
                                    viewModel.saveSession(UserModel(email, token, name))
                                    startActivity(Intent(this, MainActivity::class.java))
                                    finish()
                                }
                                is Result.Error -> {
                                    binding.progressBar.visibility = View.GONE
                                }
                            }
                        }
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
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