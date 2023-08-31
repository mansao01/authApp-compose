package com.example.authcomposeapp

import android.app.Application
import com.example.authcomposeapp.data.AppContainer
import com.example.authcomposeapp.data.DefaultAppContainer

class AuthAppApplication: Application(){
    lateinit var container:AppContainer

    override fun onCreate() {
        super.onCreate()
        container =DefaultAppContainer()
    }
}