package com.rey.dicodingstory.di

import android.content.Context
import com.rey.dicodingstory.data.StoryRepository
import com.rey.dicodingstory.data.pref.UserPreference
import com.rey.dicodingstory.data.pref.dataStore
import com.rey.dicodingstory.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return StoryRepository.getInstance(pref, apiService)
    }
}