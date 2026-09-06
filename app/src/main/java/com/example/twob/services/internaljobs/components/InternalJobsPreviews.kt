package com.example.twob.services.internaljobs.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.twob.services.internaljobs.InternalJob
import com.example.twob.services.internaljobs.InternalJobApplication
import com.example.twob.services.internaljobs.InternalJobApplicationStatus
import com.example.twob.ui.theme.TwoBTheme


private val previewJob =
    InternalJob(
        id = 1,
        title = "Finance",
        date = "1 Aug 2024",
        about =
            "Lorem ipsum dolor sit amet consectetur. Sit pellentesque at nec et in sit ac.",
        requirements =
            listOf(
                "Lorem ipsum dolor sit amet consectetur.",
                "Lorem ipsum dolor sit amet consectetur.",
                "Lorem ipsum dolor sit amet consectetur."
            ),
        note =
            "Lorem ipsum dolor sit amet consectetur."
    )

private val previewApplication =
    InternalJobApplication(
        id = 1,
        job = previewJob,
        appliedDate = "18 Sept 2024",
        status =
            InternalJobApplicationStatus
                .IN_CONSIDERATION,
        resumeName = "Resume.pdf",
        resumeUri = null,
        skills =
            "Kotlin, Android, Jetpack Compose",
        note =
            "Lorem ipsum dolor sit amet."
    )

@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 800
)
@Composable
private fun AvailableJobCardPreview() {

    TwoBTheme {

        AvailableJobCard(
            job = previewJob,
            onClick = {}
        )
    }
}

@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 800
)
@Composable
private fun AppliedJobCardPreview() {

    TwoBTheme {

        AppliedJobCard(
            application =
                previewApplication,
            onClick = {}
        )
    }
}

@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 800
)
@Composable
private fun InternalJobDetailsPreview() {

    TwoBTheme {

        InternalJobDetails(
            job = previewJob,
            onApplyNow = {}
        )
    }
}

@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 800
)


@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 800
)
@Composable
private fun ApplicationDetailsPreview() {

    TwoBTheme {

        ApplicationDetails(
            application =
                previewApplication,
            onDelete = {}
        )
    }
}

@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 800
)
@Composable
private fun DeleteApplicationDialogPreview() {

    TwoBTheme {

        DeleteApplicationDialog(
            onConfirm = {},
            onDismiss = {}
        )
    }
}