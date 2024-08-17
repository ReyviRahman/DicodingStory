package com.rey.dicodingstory.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rey.dicodingstory.data.StoryRepository
import com.rey.dicodingstory.data.pref.UserModel
import com.rey.dicodingstory.data.remote.retrofit.response.ListStoryItem
import kotlinx.coroutines.launch

class HomeFragmentViewModel(private val storyRepository: StoryRepository): ViewModel() {

    fun getAllStories(token: String): LiveData<PagingData<ListStoryItem>> = storyRepository.getAllStories(token).cachedIn(viewModelScope)

    fun getSession(): LiveData<UserModel> {
        return storyRepository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            storyRepository.logout()
        }
    }
}