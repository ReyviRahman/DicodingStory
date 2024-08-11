package com.rey.dicodingstory.di

import android.content.Context
import com.rey.dicodingstory.data.StoryRepository
import com.rey.dicodingstory.data.pref.UserPreference
import com.rey.dicodingstory.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return StoryRepository.getInstance(pref)
    }
}