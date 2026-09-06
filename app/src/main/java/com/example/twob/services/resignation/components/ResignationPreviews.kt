package com.example.twob.services.resignation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.twob.services.resignation.components.assets.AcceptedContent
import com.example.twob.services.resignation.components.assets.ApprovedAssetsContent
import com.example.twob.services.resignation.components.assets.AssetOwnersContent
import com.example.twob.services.resignation.components.common.ContactDialog
import com.example.twob.services.resignation.components.common.ResignationDatePicker
import com.example.twob.services.resignation.components.common.ResignationErrorDialog
import com.example.twob.services.resignation.components.common.ResignationHeader
import com.example.twob.services.resignation.components.common.ResignationLoadingContent
import com.example.twob.ui.theme.TwoBTheme
import com.example.twob.services.resignation.components.create.CreateResignationContent
import com.example.twob.services.resignation.components.status.ResignationStatus
import com.example.twob.services.resignation.components.status.StatusContent
import com.example.twob.services.resignation.components.submission.SubmissionContent
import java.time.LocalDate

@Preview(showBackground = true)
@Composable
private fun ResignationHeaderPreview() {
    TwoBTheme {
        ResignationHeader(
            onBack = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ResignationLoadingContentPreview() {
    TwoBTheme {
        ResignationLoadingContent()
    }
}

@Preview(showBackground = true)
@Composable
private fun ResignationErrorDialogPreview() {
    TwoBTheme {
        ResignationErrorDialog(
            message =
                "Something went wrong. Please try again later.",
            onDismiss = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ContactDialogPreview() {
    TwoBTheme {
        ContactDialog(
            onDismiss = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ResignationDatePickerPreview() {
    TwoBTheme {
        ResignationDatePicker(
            initialDate = LocalDate.now(),
            onDismiss = {},
            onApply = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ResignationCreateContentPreview() {
    TwoBTheme {
        CreateResignationContent(
            resignationDate = null,
            lastWorkingDay = null,
            reason = "",
            onReasonChanged = {},
            onDateClick = {},
            onContactClick = {},
            canProceed = false,
            onProceed = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ResignationSubmissionContentPreview() {
    TwoBTheme {
        SubmissionContent(
            isSubmitting = false,
            onSubmit = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ResignationPendingContentPreview() {
    TwoBTheme {
        StatusContent(
            status = ResignationStatus.PENDING,
            onPrimaryAction = null
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ResignationRejectedContentPreview() {
    TwoBTheme {
        StatusContent(
            status = ResignationStatus.REJECTED,
            onPrimaryAction = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ResignationApprovedContentPreview() {
    TwoBTheme {
        ApprovedAssetsContent(
            isLoading = false,
            errorMessage = null,
            onOwnersClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ResignationAssetOwnersContentPreview() {
    TwoBTheme {
        AssetOwnersContent(
            departments = emptyList(),
            onBack = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ResignationAcceptedContentPreview() {
    TwoBTheme {
        AcceptedContent()
    }
}