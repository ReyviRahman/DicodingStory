package com.rey.dicodingstory.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rey.dicodingstory.data.StoryRepository
import com.rey.dicodingstory.data.pref.UserModel
import kotlinx.coroutines.launch

class RegisterViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun postUserRegister(name: String, email: String, pwd: String) = storyRepository.postUserRegister(name, email, pwd)
    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            storyRepository.saveSession(user)
        }
    }
    fun postUserLogin(email: String, password: String) = storyRepository.postUserLogin(email, password)
}