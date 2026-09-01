package com.example.twob.data.repositories

import com.example.twob.data.remote.api.HRLetterApi
import com.example.twob.data.remote.dto.HRLetterDto
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class HRLetterRepositoryImpl(
    private val api: HRLetterApi
) : HRLetterRepository {

    override suspend fun getHRLetters(
        employeeId: Int
    ): Result<List<HRLetterRequest>> {

        return try {

            val response = api.getHRLetterRequests(
                languageOfRequest = "".toTextBody(),
                employeeId = employeeId.toString().toTextBody(),
                pageSize = "".toTextBody(),
                status = "".toTextBody(),
                filterType = "".toTextBody(),
                sortType = "".toTextBody(),
                pageNumber = "".toTextBody(),
                filterValue = "".toTextBody()
            )

            if (!response.isSuccessful) {
                return Result.failure(
                    Exception("HTTP ${response.code()}")
                )
            }

            val body = response.body()

            if (body?.success != true) {

                Result.failure(
                    Exception(
                        body?.message
                            ?: "Failed to load HR letter requests"
                    )
                )

            } else {

                Result.success(
                    body.data
                        .orEmpty()
                        .map { it.toDomain() }
                )
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun registerHRLetter(
        employeeId: Int,
        reasonForRequest: Int,
        addressTo: String,
        languageOfRequest: Int,
        note: String
    ): Result<String> {

        return try {

            val response = api.registerHRLetter(
                employeeId = employeeId.toPart("EmployeeId"),
                reasonForRequest = reasonForRequest.toPart("ReasonForRequest"),
                addressTo = addressTo.toPart("AddressTo"),
                languageOfRequest = languageOfRequest.toPart("LanguageOfRequest"),
                note = note.toPart("Note")
            )

            if (!response.isSuccessful) {

                Result.failure(
                    Exception("HTTP ${response.code()}")
                )

            } else {

                val body = response.body()

                if (body?.success == true) {

                    Result.success(
                        body.message.orEmpty()
                    )

                } else {

                    Result.failure(
                        Exception(
                            body?.message
                                ?: "Failed to submit HR letter request"
                        )
                    )
                }
            }

        } catch (e: Exception) {

            Result.failure(e)
        }
    }
    override suspend fun getHRStatuses(): Result<List<HRLetterStatus>> {
        return try {
            val response = api.getHRStatuses("en")

            if (!response.isSuccessful) {
                return Result.failure(
                    Exception("HTTP ${response.code()}")
                )
            }

            val body = response.body()

            if (body?.success != true) {
                Result.failure(
                    Exception(
                        body?.message ?: "Failed to load HR statuses"
                    )
                )
            } else {
                Result.success(
                    body.data.orEmpty().map {
                        HRLetterStatus(
                            id = it.id,
                            name = it.name.orEmpty()
                        )
                    }
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

private fun String.toTextBody(): RequestBody =
    toRequestBody(
        "text/plain".toMediaType()
    )

private fun Int.toPart(
    name: String
): MultipartBody.Part =
    MultipartBody.Part.createFormData(
        name = name,
        value = toString()
    )

private fun String.toPart(
    name: String
): MultipartBody.Part =
    MultipartBody.Part.createFormData(
        name = name,
        value = this
    )


fun HRLetterDto.toDomain(): HRLetterRequest {

    return HRLetterRequest(
        id = id ?: 0,

        status = status?.let {
            HRLetterStatus(
                id = it,
                name = hrStatusValue.orEmpty()
            )
        },

        statusText = hrStatusValue.orEmpty(),

        reasonId = reasonFrRequest,

        reasonText = statusValue.orEmpty(),

        languageId = languageOfRequest,

        languageText = languageValue.orEmpty(),

        addressTo = addressTo.orEmpty(),

        note = note.orEmpty(),

        creationTime = creationTime.orEmpty()
    )
}