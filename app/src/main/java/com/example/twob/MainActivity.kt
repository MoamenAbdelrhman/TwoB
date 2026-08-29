package com.example.twob

import android.content.res.Configuration
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.twob.data.local.datastore.UserPreferencesRepository
import com.example.twob.navigation.AppNavigation
import com.example.twob.session.SessionState
import com.example.twob.session.SessionViewModel
import com.example.twob.ui.theme.TwoBTheme
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale

class MainActivity : ComponentActivity() {

    private val sessionViewModel: SessionViewModel by viewModel()

    private val userPreferencesRepository:
            UserPreferencesRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()

        splashScreen.setKeepOnScreenCondition {
            sessionViewModel.state.value is SessionState.Loading
        }

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            val culture by userPreferencesRepository
                .culture
                .collectAsStateWithLifecycle(
                    initialValue = "en"
                )

            val baseConfiguration = LocalConfiguration.current
            val baseContext = LocalContext.current

            val locale = remember(culture) {
                Locale.forLanguageTag(culture)
            }

            // A Configuration (and matching Context) pinned to the
            // user-selected app language, independent of the device locale.
            val localizedConfiguration = remember(locale, baseConfiguration) {
                Configuration(baseConfiguration).apply {
                    setLocale(locale)
                }
            }

            val localizedContext = remember(localizedConfiguration) {
                baseContext.createConfigurationContext(localizedConfiguration)
            }

            // Derived from the locale itself (not hardcoded to "ar") so any
            // future RTL language keeps working automatically.
            val layoutDirection = remember(locale) {
                if (TextUtils.getLayoutDirectionFromLocale(locale) ==
                    View.LAYOUT_DIRECTION_RTL
                ) {
                    LayoutDirection.Rtl
                } else {
                    LayoutDirection.Ltr
                }
            }

            CompositionLocalProvider(
                LocalConfiguration provides localizedConfiguration,
                LocalContext provides localizedContext,
                LocalLayoutDirection provides layoutDirection
            ) {

                TwoBTheme {

                    AppNavigation(
                        sessionViewModel = sessionViewModel
                    )
                }
            }
        }
    }
}