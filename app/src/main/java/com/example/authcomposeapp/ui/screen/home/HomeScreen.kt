package com.example.authcomposeapp.ui.screen.home

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.authcomposeapp.network.response.GetProfileResponse
import com.example.authcomposeapp.network.response.UserDataItem
import com.example.authcomposeapp.ui.common.HomeUiState
import com.example.authcomposeapp.ui.component.ProgressbarDialog
import com.example.authcomposeapp.ui.component.UserListItem

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
    navigateToLogin: () -> Unit,
    token: String?

) {
    val context = LocalContext.current
    if (!token.isNullOrEmpty()){

        LaunchedEffect(Unit) {
            homeViewModel.getUsersAndProfile()
        }
        when (uiState) {
            is HomeUiState.Loading -> ProgressbarDialog()
            is HomeUiState.Success -> {
                val userData = uiState.userDataItem
                val profile = uiState.getProfileResponse
                val localToken = uiState.localToken

                HomeContent(
                    users = userData,
                    profile = profile,
                    homeViewModel = homeViewModel,
                    navigateToLogin = navigateToLogin
                )
                Log.d("HOME", localToken)
            }

            is HomeUiState.Error -> {
                LaunchedEffect(Unit) {
                    mToast(context = context, message = uiState.msg)
                    homeViewModel.removeAccessToken()
                    navigateToLogin()
                }
            }
        }
    }else{
        Log.d("", "")
    }
}


@Composable
fun HomeContent(
    users: List<UserDataItem>,
    profile: GetProfileResponse,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel,
    navigateToLogin: () -> Unit
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
        Button(onClick = {
            homeViewModel.removeAccessToken()
            navigateToLogin()
        }) {
            Text(text = "logout")
        }
        UserList(users = users)
    }
}

@Composable
fun UserList(
    users: List<UserDataItem>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.padding(horizontal = 16.dp)) {
        items(users) { data ->
            UserListItem(user = data, modifier = modifier.clickable {
                println(data.name)
            })
        }
    }
}

private fun mToast(message: String, context: Context) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}