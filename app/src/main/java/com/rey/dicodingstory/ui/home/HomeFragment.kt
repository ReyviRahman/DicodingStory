package com.rey.dicodingstory.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rey.dicodingstory.R
import com.rey.dicodingstory.data.Result
import com.rey.dicodingstory.databinding.FragmentHomeBinding
import com.rey.dicodingstory.ui.ViewModelFactory

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeFragmentViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeAdapter = HomeAdapter()
        binding.rvItemStory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvItemStory.setHasFixedSize(true)
        binding.rvItemStory.adapter = homeAdapter

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            viewModel.getAllStories(user.token).observe(viewLifecycleOwner) { result ->
                if (result != null) {
                    when(result) {
                        Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            homeAdapter.submitList(result.data.listStory)
                        }
                        is Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(requireActivity(), "Terjadi kesalahan ${result.error}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}