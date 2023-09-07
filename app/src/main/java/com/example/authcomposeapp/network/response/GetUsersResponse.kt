package com.example.authcomposeapp.network.response

import com.google.gson.annotations.SerializedName

data class GetUsersResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("data")
	val data: List<UserDataItem>
)

data class UserDataItem(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("email")
	val email: String
)
