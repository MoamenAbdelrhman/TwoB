package com.example.twob.profile

import androidx.annotation.StringRes
import com.example.twob.data.repositories.EmployeeManager

enum class ProfileDialog {
    MANAGER,
    EMPLOYEES,
    DEPARTMENT
}

data class ProfileState(
    val name: String = "",
    val machineCode: String = "",
    val jobTitle: String = "",
    val imageUrl: String? = null,

    val culture: String = "en",

    val isLoading: Boolean = true,

    val activeDialog: ProfileDialog? = null,

    val managers: List<EmployeeManager> = emptyList(),

    val departmentName: String = "",
    val partationName: String = "",

    val isDialogLoading: Boolean = false,

    @StringRes val dialogErrorRes: Int? = null,

    val isManager: Boolean = false,
    val hasManager: Boolean = false,

    val employees: List<EmployeeManager> = emptyList()

)