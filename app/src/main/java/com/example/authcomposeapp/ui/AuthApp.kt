package com.example.authcomposeapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.authcomposeapp.ui.navigation.Screen
import com.example.authcomposeapp.ui.screen.home.HomeScreen
import com.example.authcomposeapp.ui.screen.login.LoginScreen
import com.example.authcomposeapp.ui.screen.login.LoginViewModel
import com.example.authcomposeapp.ui.screen.register.RegisterScreen
import com.example.authcomposeapp.ui.screen.register.RegisterViewModel

@Composable
fun AuthApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
        modifier = modifier
    ) {
        composable(Screen.Login.route) {
            val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory)
            LoginScreen(
                uiState = loginViewModel.uiState,
                navigateToHomeScreen = { token ->
                    navController.navigate(Screen.Home.createRoute(token))
                },
                navigateToRegister = { navController.navigate(Screen.Register.route) }
            )
        }

        composable(Screen.Register.route) {
            val registerViewModel: RegisterViewModel =
                viewModel(factory = RegisterViewModel.Factory)
            RegisterScreen(uiState = registerViewModel.uiState)
        }
        
        composable(Screen.Home.route, arguments = listOf(navArgument("token"){
            type = NavType.StringType
        })){data ->
            val token = data.arguments?.getString("token") ?: ""
            HomeScreen(token = token)
        }
    }
}