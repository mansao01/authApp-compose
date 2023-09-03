package com.example.authcomposeapp.preferences

data class User(
    val name:String,
    val email:String,
    val password:String,
    val isLogin:Boolean,
    val accessToken:String
)