package com.example.twob.data.repositories

data class HRLetterRequest(
    val id: Int,
    val status: HRLetterStatus?,
    val statusText: String,
    val reasonId: Int?,
    val reasonText: String,
    val languageId: Int?,
    val languageText: String,
    val addressTo: String,
    val note: String,
    val creationTime: String
)

data class HRLetterStatus(
    val id: Int?,
    val name: String
)
data class HRLetterReason(
    val id: Int,
    val titleRes: Int
)

data class HRLetterLanguage(
    val id: Int,
    val titleRes: Int
)

interface HRLetterRepository {

    suspend fun getHRLetters(
        employeeId: Int
    ): Result<List<HRLetterRequest>>

    suspend fun registerHRLetter(
        employeeId: Int,
        reasonForRequest: Int,
        addressTo: String,
        languageOfRequest: Int,
        note: String
    ): Result<String>

    suspend fun getHRStatuses(): Result<List<HRLetterStatus>>
}