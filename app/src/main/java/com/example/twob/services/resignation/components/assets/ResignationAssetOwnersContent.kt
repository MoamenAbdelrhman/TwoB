package com.example.twob.services.resignation.components.assets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twob.R
import com.example.twob.services.resignation.ResignationAssetDepartment
import com.example.twob.services.resignation.ResignationColors

@Composable
fun AssetOwnersContent(
    departments: List<ResignationAssetDepartment>,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 20.dp,
                vertical = 8.dp
            )
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(
                items = departments
            ) { department ->

                Column {
                    DepartmentHeader(
                        departmentName = department.departmentName
                    )

                    department.assets.forEach { asset ->
                        AssetOwnerItem(
                            asset = asset
                        )
                    }
                }
            }
        }

        Button(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ResignationColors.Orange
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = stringResource(
                    R.string.back_to_assets
                ),
                fontSize = 12.sp
            )
        }
    }
}