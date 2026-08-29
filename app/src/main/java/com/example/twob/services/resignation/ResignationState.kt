package com.example.twob.services.resignation

data class ResignationState(
    val step: ResignationStep = ResignationStep.CREATE,

    val resignationDate: String = "",
    val lastWorkingDate: String = "",
    val reason: String = "",

    val errorMessage: String? = null,
    val isCheckingExistingRequest: Boolean = true,
    val isSubmitting: Boolean = false,

    val assets: List<ResignationAssetDepartment> = emptyList(),

    val isLoadingAssets: Boolean = false,

    val assetsErrorMessage: String? = null

) {
    val canProceed: Boolean
        get() =
            resignationDate.isNotBlank() &&
                    lastWorkingDate.isNotBlank() &&
                    reason.isNotBlank()
}