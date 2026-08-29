package com.example.twob.profile

sealed interface ProfileAction {

    data object ManagerClicked : ProfileAction

    data object DepartmentClicked : ProfileAction

    data object LanguageClicked : ProfileAction

    data class LanguageSelected(
        val culture: String
    ) : ProfileAction

    data object DismissDialog : ProfileAction

    data object LogoutClicked : ProfileAction

    data object EmployeesClicked : ProfileAction
}