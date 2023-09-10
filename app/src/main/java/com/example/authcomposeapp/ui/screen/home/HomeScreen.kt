package com.example.authcomposeapp.ui.screen.home

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.authcomposeapp.network.response.GetProfileResponse
import com.example.authcomposeapp.network.response.UserDataItem
import com.example.authcomposeapp.preferences.AuthViewModel
import com.example.authcomposeapp.ui.common.HomeUiState
import com.example.authcomposeapp.ui.component.ProgressbarDialog
import com.example.authcomposeapp.ui.component.UserListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
    authViewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory),
    navigateToLogin: () -> Unit
) {
    LaunchedEffect(Unit) {
        homeViewModel.getUsersAndProfile()
    }
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            HomeTopBar(
                scrollBehavior = scrollBehavior,
                logout = { navigateToLogin() },
                authViewModel = authViewModel
            )
        }
    ) {
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (uiState) {
                is HomeUiState.Loading -> ProgressbarDialog()
                is HomeUiState.Success -> {
                    val userData = uiState.userDataItem
                    val profile = uiState.getProfileResponse
                    val localToken = uiState.localToken

                    HomeContent(
                        users = userData,
                        profile = profile,
                    )
                    Log.d("HOME", localToken)
                }

                is HomeUiState.Error -> {
                    LaunchedEffect(Unit) {
                        mToast(context = context, message = uiState.msg)
                        authViewModel.removeAccessToken()
                        authViewModel.saveIsLoginState(false)
                        navigateToLogin()
                    }
                }
            }
        }

    }


}


@Composable
fun HomeContent(
    users: List<UserDataItem>,
    profile: GetProfileResponse,
    modifier: Modifier = Modifier,
) {
    Column {
        Text(
            text = "Welcome, ${profile.loggedInUserName}",
            modifier = modifier
                .padding(top = 16.dp)
                .padding(start = 16.dp),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.padding(bottom = 8.dp))
        UserList(users = users)
    }
}

@Composable
fun UserList(
    users: List<UserDataItem>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier.padding(horizontal = 16.dp)) {
        items(users) { data ->
            UserListItem(user = data, modifier = modifier.clickable {
                println(data.name)
            })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    logout: () -> Unit,
    authViewModel: AuthViewModel,

    ) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = { Text(text = "AuthApp") },
        actions = {
            IconButton(onClick = {
                logout()
                authViewModel.removeAccessToken()
                authViewModel.saveIsLoginState(false)
            }) {
                Icon(imageVector = Icons.Default.Logout, contentDescription = "logout")
            }
        }
    )
}

private fun mToast(message: String, context: Context) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}