package com.example.authcomposeapp.ui.screen.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun HomeScreen(
    token: String
) {

    Text(text = token)
}