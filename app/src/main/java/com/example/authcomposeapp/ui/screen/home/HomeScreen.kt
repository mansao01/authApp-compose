package com.example.authcomposeapp.ui.screen.home

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.authcomposeapp.network.response.UserDataItem
import com.example.authcomposeapp.ui.common.HomeUiState
import com.example.authcomposeapp.ui.component.ProgressbarDialog
import com.example.authcomposeapp.ui.component.UserListItem

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    token: String,
    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
) {
    LaunchedEffect(Unit){
        homeViewModel.getUsers(token)
    }
    val context = LocalContext.current
    when(uiState){
        is HomeUiState.Loading -> ProgressbarDialog()
        is HomeUiState.Success -> UserList(users = uiState.userDataItem)
        is HomeUiState.Error -> mToast(context = context, message = uiState.msg)
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

private fun mToast(message:String, context: Context){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}