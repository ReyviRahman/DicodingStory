package com.rey.dicodingstory.data

import com.rey.dicodingstory.data.pref.UserModel
import com.rey.dicodingstory.data.pref.UserPreference
import kotlinx.coroutines.flow.Flow

class StoryRepository private constructor(
    private val userPreference: UserPreference
){
    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }


    companion object {
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(
            userPreference: UserPreference
        ): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(userPreference)
            }.also { instance = it}
    }
}