package com.rey.dicodingstory.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rey.dicodingstory.data.StoryRepository
import com.rey.dicodingstory.data.pref.UserModel

class HomeFragmentViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun getAllStories(token: String) = storyRepository.getAllStories(token)

    fun getSession(): LiveData<UserModel> {
        return storyRepository.getSession().asLiveData()
    }
}