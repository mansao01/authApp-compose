package com.example.authcomposeapp.ui.screen.register

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.authcomposeapp.R
import com.example.authcomposeapp.network.request.RegisterRequest
import com.example.authcomposeapp.ui.common.RegisterUiState
import com.example.authcomposeapp.ui.component.ProgressbarDialog

@Composable
fun RegisterScreen(
    uiState: RegisterUiState,
    registerViewModel: RegisterViewModel = viewModel(factory = RegisterViewModel.Factory),
    navigateToLogin: () -> Unit
) {
    val context = LocalContext.current
    when (uiState) {
        is RegisterUiState.StandBy -> RegisterContent(registerViewModel = registerViewModel)
        is RegisterUiState.Loading -> ProgressbarDialog()
        is RegisterUiState.Success -> uiState.registerResponse.msg?.let {
            LaunchedEffect(Unit){
                mToast(
                    context = context,
                    message = it
                )
                navigateToLogin()
            }
        }

        is RegisterUiState.Error -> {
            mToast(context = context, message = uiState.msg)
            registerViewModel.getUiState()

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterContent(
    registerViewModel: RegisterViewModel,
    modifier: Modifier = Modifier,
) {
    var name by remember { mutableStateOf("") }
    var isNameNull by remember { mutableStateOf(false) }

    var email by remember { mutableStateOf("") }
    var isEmailNull by remember { mutableStateOf(false) }

    var password by remember { mutableStateOf("") }
    var isPasswordNull by remember { mutableStateOf(false) }

    var confirmPassword by remember { mutableStateOf("") }
    var isConfirmPasswordNull by remember { mutableStateOf(false) }

    Column(modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.register),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 36.sp,
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .padding(top = 48.dp)
        )
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Left,
            ),
            label = {
                Text(text = stringResource(R.string.enter_your_name))
            },
            placeholder = { Text(text = stringResource(R.string.name)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.PersonOutline,
                    contentDescription = null
                )
            },
            supportingText = {
                Text(text = stringResource(R.string.require))
            },
            isError = isNameNull,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Left
            ),
            label = {
                Text(text = stringResource(R.string.enter_your_email))
            },
            placeholder = { Text(text = stringResource(R.string.email)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Email,
                    contentDescription = null
                )
            },
            supportingText = {
                Text(text = stringResource(R.string.require))
            },
            isError = isEmailNull,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp)
        )
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Left
            ),
            label = {
                Text(text = stringResource(R.string.enter_your_password))
            },
            placeholder = { Text(text = stringResource(R.string.password)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = null
                )
            },
            supportingText = {
                Text(text = stringResource(R.string.require))
            },
            isError = isPasswordNull,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp)
        )

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
            },
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Left
            ),
            label = {
                Text(text = stringResource(R.string.enter_your_confirm_password))
            },
            placeholder = { Text(text = stringResource(R.string.password)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = null
                )
            },
            supportingText = {
                Text(text = stringResource(R.string.require))
            },
            isError = isConfirmPasswordNull,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp)
        )

        Button(
            onClick = {
                when {
                    name.isEmpty() -> isNameNull = true
                    email.isEmpty() -> isEmailNull = true
                    password.isEmpty() -> isPasswordNull = true
                    confirmPassword.isEmpty() -> isConfirmPasswordNull = true
                    else -> registerViewModel.register(
                        RegisterRequest(
                            name,
                            email,
                            password,
                            confirmPassword
                        )
                    )
                }
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(horizontal = 52.dp)
                .padding(top = 18.dp)
        ) {
            Text(text = "Post")
        }
    }
}

private fun mToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
