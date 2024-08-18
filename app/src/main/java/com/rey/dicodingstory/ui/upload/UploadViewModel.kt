package com.rey.dicodingstory.ui.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rey.dicodingstory.data.StoryRepository
import com.rey.dicodingstory.data.pref.UserModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return storyRepository.getSession().asLiveData()
    }

    fun uploadStories(token: String, file: MultipartBody.Part, description: RequestBody) = storyRepository.uploadStories(token, file, description)
}