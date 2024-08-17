package com.rey.dicodingstory.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.rey.dicodingstory.data.Result
import com.rey.dicodingstory.databinding.ActivityDetailBinding
import com.rey.dicodingstory.ui.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idUser = intent.getStringExtra(EXTRA_ID)

        viewModel.getSession().observe(this) { user ->
            if (user.isLogin) {
                viewModel.getDetailStories(user.token, idUser.toString()).observe(this) { result ->
                    when(result) {
                        Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.tvTitle.text = result.data.story.name
                            binding.tvDescription.text = result.data.story.description
                            Glide.with(this)
                                .load(result.data.story.photoUrl)
                                .into(binding.imgPoster)
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

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}