package com.example.authcomposeapp.network.response

import com.google.gson.annotations.SerializedName

data class GetProfileResponse(

	@field:SerializedName("loggedInUserName")
	val loggedInUserName: String,

	@field:SerializedName("loggedInUserEmail")
	val loggedInUserEmail: String
)
