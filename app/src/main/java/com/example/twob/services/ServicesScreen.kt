package com.example.twob.services

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.twob.R
import com.example.twob.components.AppHeader
import com.example.twob.components.HeaderViewModel
import com.example.twob.components.MainBottomNavigation
import com.example.twob.components.MainDestination
import com.example.twob.ui.theme.TwoBTheme
import com.example.twob.ui.theme.secondaryColor
import com.example.twob.ui.theme.thirdColor
import org.koin.androidx.compose.koinViewModel

private val ServicesBlue = Color(0xFF0D2E6B)
private val CardBorder = Color(0xFFE3E3E3)

enum class ServiceDestination {
    PERMISSION_REQUEST,
    OFFSITE_WORK,
    FINGERPRINT_HISTORY,
    VACATION_REQUEST,
    PENALTY_HISTORY,
    KPIS,
    OFFICIAL_HOLIDAYS,
    INTERNAL_JOBS,
    COMPANY_ASSETS,
    RESIGNATION,

    HR_LETTER_REQUEST
}

data class ServiceItem(
    val titleRes: Int,
    val iconRes: Int,
    val destination: ServiceDestination
)

private val services = listOf(
    ServiceItem(
        titleRes = R.string.permission_request,
        iconRes = R.drawable.permission__request,
        destination = ServiceDestination.PERMISSION_REQUEST
    ),
    ServiceItem(
        titleRes = R.string.offsite_work_request,
        iconRes = R.drawable.offsite_work_request,
        destination = ServiceDestination.OFFSITE_WORK
    ),
    ServiceItem(
        titleRes = R.string.fingerprint_history,
        iconRes = R.drawable.fingerprint_history,
        destination = ServiceDestination.FINGERPRINT_HISTORY
    ),
    ServiceItem(
        titleRes = R.string.vacation_request,
        iconRes = R.drawable.vacation_request,
        destination = ServiceDestination.VACATION_REQUEST
    ),
    ServiceItem(
        titleRes = R.string.penalty_history,
        iconRes = R.drawable.penalty_history,
        destination = ServiceDestination.PENALTY_HISTORY
    ),
    ServiceItem(
        titleRes = R.string.kpis_earned,
        iconRes = R.drawable.kearned,
        destination = ServiceDestination.KPIS
    ),
    ServiceItem(
        titleRes = R.string.official_holidays,
        iconRes = R.drawable.official_holidays,
        destination = ServiceDestination.OFFICIAL_HOLIDAYS
    ),
    ServiceItem(
        titleRes = R.string.internal_jobs,
        iconRes = R.drawable.internalj,
        destination = ServiceDestination.INTERNAL_JOBS
    ),
    ServiceItem(
        titleRes = R.string.company_assets,
        iconRes = R.drawable.company__assets,
        destination = ServiceDestination.COMPANY_ASSETS
    ),
    ServiceItem(
        titleRes = R.string.resignation_request,
        iconRes = R.drawable.resignation_request,
        destination = ServiceDestination.RESIGNATION
    ),
    ServiceItem(
        titleRes = R.string.hr_letter_request,
        iconRes = R.drawable.hrletter,
        destination = ServiceDestination.HR_LETTER_REQUEST
    )
)

@Composable
fun ServicesScreen(
    onServiceClick: (ServiceItem) -> Unit = {},
    onDestinationSelected: (MainDestination) -> Unit
) {

    val headerViewModel: HeaderViewModel = koinViewModel()
    val imageUrl by headerViewModel.imageUrl.collectAsStateWithLifecycle()

    var searchQuery by remember {
        mutableStateOf("")
    }

    val filteredServices = remember(searchQuery) {

        if (searchQuery.isBlank()) {
            services
        } else {
            services.filter { service ->
                service.titleRes
                    .let { titleRes ->
                        // Search logic will be connected to
                        // localized service names later.
                        titleRes != 0
                    }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        AppHeader(
            imageUrl = imageUrl
        )

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        ServicesSearchBar(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
            }
        )

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(
                start = 19.dp,
                end = 19.dp,
                bottom = 14.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(9.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            items(
                items = filteredServices,
                key = {
                    it.titleRes
                }
            ) { service ->

                ServiceCard(
                    service = service,
                    onClick = {
                        onServiceClick(service)
                    }
                )
            }
        }

        MainBottomNavigation(
            selectedDestination = MainDestination.SERVICES,
            onDestinationSelected = onDestinationSelected
        )
    }
}

@Composable
private fun ServicesHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(
                color = ServicesBlue,
                shape = RoundedCornerShape(
                    bottomStart = 38.dp,
                    bottomEnd = 38.dp
                )
            )
            .padding(horizontal = 18.dp),
        contentAlignment = Alignment.CenterStart
    )  {
        Image(
            painter = painterResource(
                id = R.drawable.logo_2b
            ),
            contentDescription = "2B logo",
            modifier = Modifier
                .padding(start = 8.dp, bottom = 8.dp)
                .size(56.dp)
                .align(Alignment.BottomStart),
            contentScale = ContentScale.Fit
        )
        Image(
            imageVector = Icons.Outlined.PersonOutline,
            contentDescription = "Profile image",
            modifier = Modifier
                .padding(end = 8.dp, bottom = 10.dp)
                .size(42.dp)
                .align(Alignment.BottomEnd),
            contentScale = ContentScale.Fit
        )

    }
}

@Composable
private fun ServicesSearchBar(
    value: String,
    onValueChange: (String) -> Unit
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 19.dp)
            .height(36.dp),
        singleLine = true,
        leadingIcon = {

            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null,
                tint = Color(0xFF9E9E9E),
                modifier = Modifier.size(18.dp)
            )
        },
        placeholder = {

            Text(
                text = stringResource(R.string.search),
                color = Color(0xFFB0B0B0),
                fontSize = 10.sp
            )
        },
        textStyle = androidx.compose.ui.text.TextStyle(
            fontSize = 10.sp,
            color = secondaryColor
        ),
        shape = RoundedCornerShape(20.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFB5B5B5),
            unfocusedBorderColor = Color(0xFFB5B5B5),
            cursorColor = thirdColor
        )
    )
}

@Composable
private fun ServiceCard(
    service: ServiceItem,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(
                RoundedCornerShape(5.dp)
            )
            .shadow(8.dp, RoundedCornerShape(5.dp))
            .border(
                width = 1.dp,
                color = CardBorder,
                shape = RoundedCornerShape(5.dp)

            )
            .background(Color.White)
            .clickable(
                onClick = onClick
            )
            .padding(
                top = 12.dp,
                bottom = 7.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(63.dp)
            ,
            contentAlignment = Alignment.Center
        ) {


            Icon(
                painter = painterResource(
                    id = service.iconRes
                ),
                contentDescription = null,
                modifier = Modifier.size(60.dp),
                tint = Color.Unspecified
            )

        }

        Spacer(
            modifier = Modifier.height(7.dp)
        )

        Text(
            text = stringResource(service.titleRes),
            color = secondaryColor,
            fontSize = 11.sp,
            textAlign = TextAlign.Center,
            lineHeight = 16.sp,
            maxLines = 2
        )
    }
}



@Preview(showBackground = true)
@Composable
private fun ServicesScreenPreview() {

    TwoBTheme {
        ServicesScreen(
            onDestinationSelected = {}
        )
    }
}