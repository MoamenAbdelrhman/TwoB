package com.example.twob.data.repositories

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import com.example.twob.data.local.datastore.UserPreferencesRepository
import com.example.twob.data.remote.NetworkResult
import com.example.twob.data.remote.api.InternalJobsApi
import com.example.twob.data.remote.dto.InternalJobApplicationDataDto
import com.example.twob.data.remote.dto.InternalJobDto
import com.example.twob.data.remote.safeApiCall
import com.example.twob.services.internaljobs.InternalJob
import com.example.twob.services.internaljobs.InternalJobApplication
import com.example.twob.services.internaljobs.InternalJobApplicationStatus
import com.example.twob.services.internaljobs.InternalJobsRepository
import kotlinx.coroutines.flow.first
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class InternalJobsRepositoryImpl(
    private val internalJobsApi: InternalJobsApi,
    private val userPreferencesRepository:
    UserPreferencesRepository,
    private val context: Context
) : InternalJobsRepository {

    override suspend fun getAvailableJobs():
            NetworkResult<List<InternalJob>> {

        return safeApiCall {

            val response =
                internalJobsApi.getAvailableJobs()

            if (!response.isSuccessful) {
                error(
                    "Failed to load available jobs"
                )
            }

            val data =
                response.body()
                    ?: error(
                        "Empty response from server"
                    )

            data.map {
                it.toDomain()
            }
        }
    }

    override suspend fun getApplications():
            NetworkResult<List<InternalJobApplication>> {

        return safeApiCall {

            val employeeId =
                userPreferencesRepository
                    .employeeId
                    .first()
                    ?: error(
                        "Employee ID is not available"
                    )

            val response =
                internalJobsApi.getApplications(
                    employeeId =
                        textRequestBody(
                            employeeId.toString()
                        )
                )

            if (!response.isSuccessful) {
                error(
                    "Failed to load applications"
                )
            }

            val body =
                response.body()
                    ?: error(
                        "Empty response from server"
                    )

            if (!body.success) {
                error(
                    body.message.ifBlank {
                        "Failed to load applications"
                    }
                )
            }

            body.data.map {
                it.toDomain()
            }
        }
    }

    override suspend fun getApplicationById(
        id: Int
    ): NetworkResult<InternalJobApplication> {

        return safeApiCall {

            val response =
                internalJobsApi
                    .getApplicationById(id)

            if (!response.isSuccessful) {
                error(
                    "Failed to load application"
                )
            }

            val body =
                response.body()
                    ?: error(
                        "Empty response from server"
                    )

            if (!body.success) {
                error(
                    body.message.ifBlank {
                        "Failed to load application"
                    }
                )
            }

            val data =
                body.data
                    ?: error(
                        "Application data not found"
                    )

            data.toDomain()
        }
    }

    override suspend fun registerApplication(
        jobId: Int,
        notes: String,
        skills: String,
        resumeUri: Uri
    ): NetworkResult<Unit> {

        return safeApiCall {

            val employeeId =
                userPreferencesRepository
                    .employeeId
                    .first()
                    ?: error(
                        "Employee ID is not available"
                    )

            val response =
                internalJobsApi.registerApplication(

                    employeeId =
                        textRequestBody(
                            employeeId.toString()
                        ),

                    internalJobId =
                        textRequestBody(
                            jobId.toString()
                        ),

                    notes =
                        textRequestBody(notes),

                    skills =
                        textRequestBody(skills),

                    uploadCV =
                        createResumePart(
                            resumeUri
                        ),

                    applicationState =
                        null
                )

            if (!response.isSuccessful) {
                error(
                    "Failed to submit job application"
                )
            }

            val body =
                response.body()
                    ?: error(
                        "Empty response from server"
                    )

            if (!body.success) {
                error(
                    body.message.ifBlank {
                        "Failed to submit job application"
                    }
                )
            }

            Unit
        }
    }

    override suspend fun deleteApplication(
        id: Int
    ): NetworkResult<Unit> {

        return safeApiCall {

            val response =
                internalJobsApi
                    .deleteApplication(id)

            if (!response.isSuccessful) {
                error(
                    "Failed to delete application"
                )
            }

            val body =
                response.body()
                    ?: error(
                        "Empty response from server"
                    )

            if (!body.success) {
                error(
                    body.message.ifBlank {
                        "Failed to delete application"
                    }
                )
            }

            Unit
        }
    }

    private fun createResumePart(
        uri: Uri
    ): MultipartBody.Part {

        val fileName =
            getFileName(uri)

        val mimeType =
            context.contentResolver
                .getType(uri)
                ?: "application/octet-stream"

        val bytes =
            context.contentResolver
                .openInputStream(uri)
                ?.use { inputStream ->
                    inputStream.readBytes()
                }
                ?: error(
                    "Unable to read selected resume"
                )

        val requestBody =
            bytes.toRequestBody(
                mimeType.toMediaType()
            )

        return MultipartBody.Part.createFormData(
            "UploadCV",
            fileName,
            requestBody
        )
    }

    private fun getFileName(
        uri: Uri
    ): String {

        val projection =
            arrayOf(
                OpenableColumns.DISPLAY_NAME
            )

        context.contentResolver
            .query(
                uri,
                projection,
                null,
                null,
                null
            )
            ?.use { cursor ->

                val nameIndex =
                    cursor.getColumnIndex(
                        OpenableColumns.DISPLAY_NAME
                    )

                if (
                    cursor.moveToFirst() &&
                    nameIndex >= 0
                ) {
                    return cursor.getString(
                        nameIndex
                    )
                }
            }

        return "Resume.pdf"
    }

    private fun textRequestBody(
        value: String
    ): RequestBody {

        return value.toRequestBody(
            "text/plain".toMediaType()
        )
    }
}

private fun InternalJobDto.toDomain():
        InternalJob {

    return InternalJob(
        id = id,

        title =
            nameOfJob.orEmpty(),

        date =
            formatApiDate(date),

        about =
            description.orEmpty(),

        requirements =
            jobRequirements.orEmpty(),

        note =
            notes.orEmpty()
    )
}

private fun InternalJobApplicationDataDto.toDomain():
        InternalJobApplication {

    val job = InternalJob(
        id = internalJobId ?: 0,
        title = nameOfJob.orEmpty(),
        date = formatApiDate(creationTime),
        about = "",
        requirements = emptyList(),
        note = ""
    )

    return InternalJobApplication(

        id = id,

        job = job,

        appliedDate =
            formatApiDate(
                creationTime
            ),

        status =
            mapApplicationStatus(
                internalJobApplicationStateValue,
                applicationState
            ),

        resumeName =
            uploadCV
                ?.substringAfterLast("/")
                ?.takeIf {
                    it.isNotBlank()
                }
                ?: "Resume.pdf",

        resumeUri =
            uploadCV,

        skills =
            skills.orEmpty(),

        note =
            notes.orEmpty()
    )
}

private fun mapApplicationStatus(
    stateValue: String?,
    applicationState: Int?
): InternalJobApplicationStatus {

    return when {

        stateValue.equals(
            "InConsideration",
            ignoreCase = true
        ) ||
                stateValue.equals(
                    "In Consideration",
                    ignoreCase = true
                ) ->
            InternalJobApplicationStatus
                .IN_CONSIDERATION

        stateValue.equals(
            "Hired",
            ignoreCase = true
        ) ->
            InternalJobApplicationStatus
                .HIRED

        stateValue.equals(
            "NotSelected",
            ignoreCase = true
        ) ||
                stateValue.equals(
                    "Not Selected",
                    ignoreCase = true
                ) ->
            InternalJobApplicationStatus
                .NOT_SELECTED

        else ->
            InternalJobApplicationStatus
                .APPLIED
    }
}

private fun formatApiDate(
    value: String?
): String {

    if (value.isNullOrBlank()) {
        return ""
    }

    return try {

        LocalDateTime
            .parse(
                value,
                DateTimeFormatter
                    .ISO_LOCAL_DATE_TIME
            )
            .format(
                DateTimeFormatter.ofPattern(
                    "d MMM yyyy",
                    Locale.ENGLISH
                )
            )

    } catch (_: Exception) {
        value
    }
}