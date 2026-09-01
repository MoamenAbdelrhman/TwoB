package com.example.twob.services.hrletter

import com.example.twob.data.repositories.HRLetterLanguage
import com.example.twob.data.repositories.HRLetterReason
import com.example.twob.data.repositories.HRLetterRequest
import com.example.twob.data.repositories.HRLetterStatus

enum class HRLetterScreen {
    LIST,
    CREATE
}

data class HRLetterRequestState(
    val isLoading: Boolean = false,

    val requests: List<HRLetterRequest> = emptyList(),

    val statuses: List<HRLetterStatus> = emptyList(),

    val hasLoadedRequests: Boolean = false,

    val screen: HRLetterScreen = HRLetterScreen.LIST,

    val selectedStatus: HRLetterStatus? = null,

    val selectedReason: HRLetterReason? = null,
    val addressTo: String = "",
    val selectedLanguage: HRLetterLanguage? = null,
    val note: String = "",

    val showReasonDialog: Boolean = false,
    val showLanguageDialog: Boolean = false,

    val isSubmitting: Boolean = false,

    val errorMessage: String? = null,
    val successMessage: String? = null
)