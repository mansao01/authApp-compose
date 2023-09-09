package com.example.authcomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.authcomposeapp.ui.AuthApp
import com.example.authcomposeapp.ui.theme.AuthComposeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val application = application as AuthAppApplication
        val authTokenManager = application.authTokenManager
        super.onCreate(savedInstanceState)
        setContent {
            val token = remember{ mutableStateOf("") }
            authTokenManager.token
                .collectAsState(initial = "")
                .value
                .also { collectedState ->
                    token.value = collectedState
                }
            AuthComposeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AuthApp(token = token.value)
                }
            }
        }
    }
}