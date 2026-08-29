package com.example.twob.data.local.datastore

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    val token: Flow<String?>

    val employeeId: Flow<Int?>
    val machineCode: Flow<String?>
    val employeeName: Flow<String?>
    val imageUrl: Flow<String?>
    val jobName: Flow<String?>
    val role: Flow<String?>

    val shiftId: Flow<Int?>

    val culture: Flow<String>

    suspend fun saveToken(token: String)

    suspend fun saveUserProfile(
        employeeId: Int,
        machineCode: String,
        name: String,
        imageUrl: String?,
        jobName: String,
        role: String,
        shiftId: Int
    )

    suspend fun saveCulture(culture: String)

    suspend fun clearUserData()

    suspend fun initializeToken()
}