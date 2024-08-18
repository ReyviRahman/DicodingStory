package com.rey.dicodingstory.data.remote.retrofit.response

import com.google.gson.annotations.SerializedName

data class UploadStoriesResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
