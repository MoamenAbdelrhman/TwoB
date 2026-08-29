package com.example.twob.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.example.twob.ui.theme.secondaryColor

private val ErrorRed = Color(0xFFFF3B30)

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    supportingText: String? = null,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onPasswordVisibilityChanged: (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth(),
        singleLine = true,

        label = {
            Text(
                text = label,
                fontSize = 14.sp
            )
        },

        isError = isError,

        supportingText = supportingText?.let {
            {
                Text(
                    text = it,
                    color = ErrorRed,
                    fontSize = 9.sp
                )
            }
        },

        visualTransformation =
            if (isPassword && !passwordVisible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },

        trailingIcon =
            if (isPassword) {

                {
                    IconButton(
                        onClick = {
                            onPasswordVisibilityChanged?.invoke()
                        }
                    ) {

                        Icon(
                            imageVector =
                                if (passwordVisible) {
                                    Icons.Outlined.VisibilityOff
                                } else {
                                    Icons.Outlined.Visibility
                                },
                            contentDescription =
                                "Password visibility",
                            tint = Color(0xFF9E9E9E)
                        )
                    }
                }

            } else {
                null
            },

        keyboardOptions = keyboardOptions,

        colors = OutlinedTextFieldDefaults.colors(

            focusedBorderColor = secondaryColor,
            unfocusedBorderColor = secondaryColor,

            errorBorderColor = ErrorRed,

            focusedLabelColor = secondaryColor,
            unfocusedLabelColor = secondaryColor,

            errorLabelColor = ErrorRed,

            focusedTextColor = secondaryColor,
            unfocusedTextColor = secondaryColor,

            errorTextColor = secondaryColor,

            cursorColor = secondaryColor,

            focusedTrailingIconColor = Color(0xFF9E9E9E),
            unfocusedTrailingIconColor = Color(0xFF9E9E9E),
            errorTrailingIconColor = Color(0xFF9E9E9E),

            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            errorContainerColor = Color.White
        )
    )
}