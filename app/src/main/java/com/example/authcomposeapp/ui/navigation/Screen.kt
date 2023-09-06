package com.example.authcomposeapp.ui.navigation

sealed class Screen(val route:String){

    object Login:Screen("login")
    object Register:Screen("register")
    object Home:Screen("login/{token}"){
        fun createRoute(token:String) ="login/$token"
    }

}
