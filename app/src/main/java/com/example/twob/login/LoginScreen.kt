package com.example.twob.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.twob.R
import com.example.twob.components.AppTextField
import com.example.twob.ui.theme.TwoBTheme
import com.example.twob.ui.theme.background
import com.example.twob.ui.theme.secondaryColor
import com.example.twob.ui.theme.thirdColor
import org.koin.androidx.compose.koinViewModel

private val ErrorRed = Color(0xFFFF3B30)
private val DisabledGray = Color(0xFF9E9E9E)

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {

        viewModel.effect.collect { effect ->

            when (effect) {

                LoginEffect.NavigateToProfile -> {
                    onLoginSuccess()
                }
            }
        }
    }

    LoginContent(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun LoginContent(
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                )
                .imePadding()
                .padding(
                    horizontal = 10.dp,
                    vertical = 32.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Image(
                painter = painterResource(
                    id = R.drawable.logo_2b
                ),
                contentDescription = "2B Logo",
                modifier = Modifier.size(82.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(
                modifier = Modifier.height(44.dp)
            )

            LoginCard(
                state = state,
                onAction = onAction
            )
        }
    }
}

@Composable
private fun LoginCard(
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(9.dp)
            )
            .padding(
                horizontal = 20.dp,
                vertical = 20.dp
            ),
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = stringResource(R.string.login_title),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = secondaryColor
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = stringResource(R.string.login_subtitle),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            color = Color.Gray
        )

        Spacer(
            modifier = Modifier.height(35.dp)
        )

        AppTextField(
            value = state.id,
            onValueChange = {
                onAction(
                    LoginAction.IdChanged(it)
                )
            },
            label = stringResource(R.string.login_id_label),
            isError = state.isLoginError
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        AppTextField(
            value = state.password,
            onValueChange = {
                onAction(
                    LoginAction.PasswordChanged(it)
                )
            },
            label = stringResource(R.string.login_password_label),
            isError = state.isLoginError,
            isPassword = true,
            passwordVisible = state.isPasswordVisible,
            onPasswordVisibilityChanged = {
                onAction(
                    LoginAction.TogglePasswordVisibility
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            )
        )
        Spacer(
            modifier = Modifier.height(12.dp)
        )

        if (state.isLoginError) {
            Text(
                text = state.errorMessageRes?.let { stringResource(it) }
                    ?: state.errorMessage
                    ?: stringResource(R.string.login_default_error),
                fontSize = 9.sp,
                color = ErrorRed,
                modifier = Modifier.padding(start = 4.dp, top = 3.dp)
            )
        }

        Spacer(
            modifier = Modifier.height(
                if (state.isLoginError) {
                    22.dp
                } else {
                    18.dp
                }
            )
        )

        FingerprintRow(
            checked = state.useFingerprint,
            enabled = true,
            onCheckedChange = {
                onAction(
                    LoginAction.ToggleFingerprint
                )
            }
        )

        Spacer(
            modifier = Modifier.height(
                if (state.isLoginError) {
                    15.dp
                } else {
                    16.dp
                }
            )
        )

        LoginButton(
            isLoading = state.isLoading,
            onClick = {
                onAction(
                    LoginAction.LoginClicked
                )
            }
        )
    }
}

@Composable
private fun LoginButton(
    isLoading: Boolean,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,
        enabled = !isLoading,
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = thirdColor,
            disabledContainerColor = thirdColor
        ),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(
            horizontal = 16.dp,
            vertical = 0.dp
        )
    ) {

        Text(
            text = if (isLoading) {
                stringResource(R.string.login_loading)
            } else {
                stringResource(R.string.login_button)
            },
            fontSize = 12.sp,
            color = Color.White
        )
    }
}

@Composable
private fun FingerprintRow(
    checked: Boolean,
    enabled: Boolean,
    onCheckedChange: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Checkbox(
            checked = checked,
            onCheckedChange = {
                onCheckedChange()
            },
            enabled = enabled,
            modifier = Modifier.size(24.dp),
            colors = CheckboxDefaults.colors(
                checkedColor = secondaryColor,
                uncheckedColor = if (enabled) {
                    Color(0xFF9E9E9E)
                } else {
                    Color(0xFFBDBDBD)
                },
                checkmarkColor = Color.White,
                disabledCheckedColor = Color(0xFFBDBDBD),
                disabledUncheckedColor = Color(0xFFBDBDBD)
            )
        )

        Spacer(
            modifier = Modifier.width(1.dp)
        )

        Text(
            text = stringResource(R.string.fingerprint_login_label), // was "Use Fingerprint to Log In (Next time)"
            fontSize = 9.sp,
            color = if (enabled) {
                secondaryColor
            } else {
                DisabledGray
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {

    TwoBTheme {

        LoginContent(
            state = LoginState(
                id = "Hussein",
                password = "1223446779",
                isPasswordVisible = true,
                useFingerprint = true
            ),
            onAction = {}
        )
    }
}