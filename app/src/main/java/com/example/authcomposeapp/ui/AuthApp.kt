package com.example.authcomposeapp.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.authcomposeapp.ui.screen.register.RegisterScreen
import com.example.authcomposeapp.ui.screen.register.RegisterViewModel

@Composable
fun AuthApp() {
    val registerViewModel: RegisterViewModel = viewModel(factory = RegisterViewModel.Factory)
    RegisterScreen(uiState = registerViewModel.uiState)
}