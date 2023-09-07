package com.example.authcomposeapp.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.authcomposeapp.network.response.UserDataItem

@Composable
fun UserListItem(
    user:UserDataItem,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth().padding(vertical = 2.dp)) {
        Column {
            Text(text = user.name, modifier = Modifier.padding(top = 4.dp))
            Text(text = user.email, modifier = Modifier.padding(vertical = 4.dp))
        }
    }

}