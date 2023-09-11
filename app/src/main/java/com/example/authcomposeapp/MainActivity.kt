package com.example.authcomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.authcomposeapp.preferences.AuthViewModel
import com.example.authcomposeapp.ui.AuthApp
import com.example.authcomposeapp.ui.theme.AuthComposeAppTheme

class MainActivity : ComponentActivity() {
    private val authViewModel by viewModels<AuthViewModel> {
        AuthViewModel.Factory
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition{
            !authViewModel.isLoading.value
        }
        setContent {
            AuthComposeAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val startDestinationScreen by authViewModel.startDestination
                    AuthApp(
                        startDestination = startDestinationScreen
                    )
                }
            }
        }
    }
}