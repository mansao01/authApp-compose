package com.example.authcomposeapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.authcomposeapp.ui.navigation.Screen
import com.example.authcomposeapp.ui.screen.home.HomeScreen
import com.example.authcomposeapp.ui.screen.home.HomeViewModel
import com.example.authcomposeapp.ui.screen.login.LoginScreen
import com.example.authcomposeapp.ui.screen.login.LoginViewModel
import com.example.authcomposeapp.ui.screen.register.RegisterScreen
import com.example.authcomposeapp.ui.screen.register.RegisterViewModel

@Composable
fun AuthApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String
) {
//    val authViewModel:AuthViewModel = viewModel(factory = AuthViewModel.Factory)
//    val startDestination = if (!token.isNullOrEmpty()) Screen.Home.route else Screen.Login.route
//    val startDestination by authViewModel.startDestination
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Login.route) {
            val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory)
            LoginScreen(
                uiState = loginViewModel.uiState,
                navigateToHomeScreen = {
                    navController.navigate(Screen.Home.route)
                },
                navigateToRegister = {
                    navController.popBackStack()
                    navController.navigate(Screen.Register.route)
                },
            )
        }

        composable(Screen.Register.route) {
            val registerViewModel: RegisterViewModel =
                viewModel(factory = RegisterViewModel.Factory)
            RegisterScreen(
                uiState = registerViewModel.uiState,
                navigateToLogin = {
                    navController.popBackStack()
                    navController.navigate(Screen.Login.route)
                }
            )

        }

        composable(Screen.Home.route) {
            val homeViewModel: HomeViewModel =
                viewModel(factory = HomeViewModel.Factory)
            HomeScreen(
                homeViewModel = homeViewModel,
                uiState = homeViewModel.uiState,
                navigateToLogin = {
                    navController.navigate(Screen.Login.route)
                    navController.popBackStack()
                },
            )
        }
    }
}