package com.example.authcomposeapp.ui.screen.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.authcomposeapp.R
import com.example.authcomposeapp.network.request.LoginRequest
import com.example.authcomposeapp.ui.common.LoginUiState
import com.example.authcomposeapp.ui.component.ProgressbarDialog

@Composable
fun LoginScreen(
    uiState: LoginUiState,
    loginViewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory),
    navigateToHomeScreen: (String) -> Unit,
    navigateToRegister: () -> Unit
) {
    val context = LocalContext.current

    when (uiState) {
        is LoginUiState.StandBy -> LoginContent(
            loginViewModel = loginViewModel,
            navigateToRegister = navigateToRegister
        )

        is LoginUiState.Loading -> ProgressbarDialog()
//        is LoginUiState.Success -> uiState.loginResponse.accessToken?.let { navigateToHomeScreen(it) }
        is LoginUiState.Success -> mToast(context, uiState.loginResponse.toString())
        is LoginUiState.Error -> {
            mToast(context, uiState.msg)
            loginViewModel.getUiState()
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginContent(
    loginViewModel: LoginViewModel,
    modifier: Modifier = Modifier,
    navigateToRegister: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isEmailEmpty by remember { mutableStateOf(false) }
    var isPasswordEmpty by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.login),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 36.sp,
            modifier = Modifier.padding(top = 48.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = stringResource(R.string.enter_your_email)) },
            placeholder = { Text(text = stringResource(R.string.email)) },
            leadingIcon = { Icon(imageVector = Icons.Outlined.Email, contentDescription = null) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            isError = isEmailEmpty,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = stringResource(R.string.enter_your_password)) },
            placeholder = { Text(text = stringResource(R.string.password)) },
            leadingIcon = { Icon(imageVector = Icons.Outlined.Lock, contentDescription = null) },
            visualTransformation = PasswordVisualTransformation(),
            isError = isPasswordEmpty,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Register",
                textDecoration = TextDecoration.Underline,
                style = MaterialTheme.typography.bodySmall,
                color = if (isEmailEmpty || isPasswordEmpty) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(start = 52.dp)
                    .clickable { navigateToRegister() }
            )

            Button(
                onClick = {
                    when{
                        email.isEmpty() -> isEmailEmpty = true
                        password.isEmpty() -> isPasswordEmpty = true
                        else -> loginViewModel.login(LoginRequest(email, password))
                    }

                },
                modifier = Modifier
                    .padding(top = 18.dp)
                    .padding(end = 52.dp)

            ) {
                Text(text = "Login")
            }
        }
    }
}

private fun mToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}