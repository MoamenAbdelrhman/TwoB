package com.example.twob.services.resignation.components.create

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.twob.R

@Composable
fun ResignationIllustration() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(
                id = R.drawable.cuate
            ),
            contentDescription = null,
            modifier = Modifier.size(150.dp),
            tint = Color.Unspecified
        )
    }
}