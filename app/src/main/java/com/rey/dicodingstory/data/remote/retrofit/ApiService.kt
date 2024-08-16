package com.rey.dicodingstory.data.remote.retrofit

import com.rey.dicodingstory.data.remote.retrofit.response.GetAllStoryResponse
import com.rey.dicodingstory.data.remote.retrofit.response.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("stories")
    suspend fun getStories(
        @Header("Authorization") token: String,
//        @Query("page") page: Int = 1,
    ): GetAllStoryResponse
}