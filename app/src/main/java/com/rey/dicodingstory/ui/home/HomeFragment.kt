package com.rey.dicodingstory.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.rey.dicodingstory.R
import com.rey.dicodingstory.data.LoadingStateAdapter
import com.rey.dicodingstory.data.Result
import com.rey.dicodingstory.data.StoryPagingSource
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
        binding.rvItemStory.adapter = homeAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                homeAdapter.retry()
            }
        )

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (user.isLogin) {
                viewModel.getAllStories(user.token).observe(viewLifecycleOwner) {
                    homeAdapter.submitData(lifecycle, it)
                }
            }
        }

        StoryPagingSource.isLoading.observe(viewLifecycleOwner) {
            if (it == true) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.logout -> {
                    MaterialAlertDialogBuilder(requireActivity())
                        .setTitle(resources.getString(R.string.logout))
                        .setMessage(resources.getString(R.string.logout_meesage))
                        .setNegativeButton(resources.getString(R.string.no)) { dialog, which ->

                        }
                        .setPositiveButton(resources.getString(R.string.yes)) { dialog, which ->
                            viewModel.logout()
                        }
                        .show()
                    true
                }

                else -> false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}