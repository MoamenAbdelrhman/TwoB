package com.example.twob.data.local.datastore

import android.content.Context
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.twob.data.local.datastore.UserPreferencesKeys.CULTURE
import com.example.twob.data.remote.auth.TokenProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(
    name = "user_preferences"
)

private object UserPreferencesKeys {
    val TOKEN =
        stringPreferencesKey("token")

    val CULTURE =
        stringPreferencesKey("culture")

    val EMPLOYEE_ID =
        intPreferencesKey("employee_id")

    val SHIFT_ID =
        intPreferencesKey("shift_id")

    val MACHINE_CODE =
        stringPreferencesKey("machine_code")

    val EMPLOYEE_NAME =
        stringPreferencesKey("employee_name")

    val IMAGE_URL =
        stringPreferencesKey("image_url")

    val JOB_NAME =
        stringPreferencesKey("job_name")

    val ROLE =
        stringPreferencesKey("role")
}

class UserPreferencesRepositoryImpl(
    private val context: Context,
    private val tokenProvider: TokenProvider
) : UserPreferencesRepository {

    override val token: Flow<String?> = context.dataStore.data
        .map { preferences -> preferences[UserPreferencesKeys.TOKEN] }

    override suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[UserPreferencesKeys.TOKEN] = token
        }
        tokenProvider.updateToken(token)
    }

    override suspend fun clearUserData() {

        context.dataStore.edit { preferences ->

            preferences.remove(
                UserPreferencesKeys.TOKEN
            )

            preferences.remove(
                UserPreferencesKeys.EMPLOYEE_ID
            )

            preferences.remove(
                UserPreferencesKeys.MACHINE_CODE
            )

            preferences.remove(
                UserPreferencesKeys.EMPLOYEE_NAME
            )

            preferences.remove(
                UserPreferencesKeys.IMAGE_URL
            )

            preferences.remove(
                UserPreferencesKeys.JOB_NAME
            )

            preferences.remove(
                UserPreferencesKeys.ROLE
            )

            preferences.remove(
                UserPreferencesKeys.SHIFT_ID
            )
        }

        tokenProvider.clearToken()
    }

    override suspend fun initializeToken() {
        tokenProvider.updateToken(token.firstOrNull())
    }

    override val culture: Flow<String> =
        context.dataStore.data.map { preferences ->
            preferences[CULTURE] ?: "en"
        }

    override suspend fun saveCulture(culture: String) {
        context.dataStore.edit { preferences ->
            preferences[CULTURE] = culture
        }
    }



    override val employeeId: Flow<Int?> =
        context.dataStore.data.map { preferences ->
            preferences[UserPreferencesKeys.EMPLOYEE_ID]
        }

    override val shiftId: Flow<Int?> =
        context.dataStore.data.map { preferences ->
            preferences[UserPreferencesKeys.SHIFT_ID]
        }

    override val machineCode: Flow<String?> =
        context.dataStore.data.map { preferences ->
            preferences[UserPreferencesKeys.MACHINE_CODE]
        }

    override val employeeName: Flow<String?> =
        context.dataStore.data.map { preferences ->
            preferences[UserPreferencesKeys.EMPLOYEE_NAME]
        }

    override val imageUrl: Flow<String?> =
        context.dataStore.data.map { preferences ->
            preferences[UserPreferencesKeys.IMAGE_URL]
        }

    override val jobName: Flow<String?> =
        context.dataStore.data.map { preferences ->
            preferences[UserPreferencesKeys.JOB_NAME]
        }

    override val role: Flow<String?> =
        context.dataStore.data.map { preferences ->
            preferences[UserPreferencesKeys.ROLE]
        }



    override suspend fun saveUserProfile(
        employeeId: Int,
        machineCode: String,
        name: String,
        imageUrl: String?,
        jobName: String,
        role: String,
        shiftId: Int
    ) {
        context.dataStore.edit { preferences ->

            preferences[UserPreferencesKeys.EMPLOYEE_ID] =
                employeeId

            preferences[UserPreferencesKeys.MACHINE_CODE] =
                machineCode

            preferences[UserPreferencesKeys.EMPLOYEE_NAME] =
                name

            if (imageUrl.isNullOrBlank()) {
                preferences.remove(
                    UserPreferencesKeys.IMAGE_URL
                )
            } else {
                preferences[UserPreferencesKeys.IMAGE_URL] =
                    imageUrl
            }

            preferences[UserPreferencesKeys.JOB_NAME] =
                jobName

            preferences[UserPreferencesKeys.ROLE] =
                role

            preferences[UserPreferencesKeys.SHIFT_ID] =
                shiftId
        }
    }
}
