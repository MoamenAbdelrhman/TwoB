package com.example.twob.services.hrletter

import com.example.twob.data.repositories.HRLetterLanguage
import com.example.twob.data.repositories.HRLetterReason
import com.example.twob.data.repositories.HRLetterStatus

sealed interface HRLetterRequestAction {

    data object AddRequestClicked : HRLetterRequestAction

    data object BackClicked : HRLetterRequestAction

    data object ReasonClicked : HRLetterRequestAction

    data object DismissReasonDialog : HRLetterRequestAction

    data class ReasonSelected(
        val reason: HRLetterReason
    ) : HRLetterRequestAction

    data object LanguageClicked : HRLetterRequestAction

    data object DismissLanguageDialog : HRLetterRequestAction

    data class LanguageSelected(
        val language: HRLetterLanguage
    ) : HRLetterRequestAction

    data class AddressChanged(
        val value: String
    ) : HRLetterRequestAction

    data class NoteChanged(
        val value: String
    ) : HRLetterRequestAction

    data object SubmitClicked : HRLetterRequestAction

    data object DismissError : HRLetterRequestAction

    data object DismissSuccess : HRLetterRequestAction



    data class StatusSelected(
        val status: HRLetterStatus?
    ) : HRLetterRequestAction
}