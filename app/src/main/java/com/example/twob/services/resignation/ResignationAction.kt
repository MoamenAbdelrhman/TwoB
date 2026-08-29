package com.example.twob.services.resignation

sealed interface ResignationAction {

    data class ResignationDateChanged(
        val date: String
    ) : ResignationAction

    data class LastWorkingDateChanged(
        val date: String
    ) : ResignationAction

    data class ReasonChanged(
        val reason: String
    ) : ResignationAction

    data object ProceedToNextStep : ResignationAction

    data object SubmitResignation : ResignationAction

    data object DismissError : ResignationAction


    data object SeeAssetOwners : ResignationAction

    data object BackToApproved : ResignationAction
}