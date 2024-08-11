package com.rey.dicodingstory.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rey.dicodingstory.data.StoryRepository
import com.rey.dicodingstory.data.pref.UserModel

class MainViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return storyRepository.getSession().asLiveData()
    }
}