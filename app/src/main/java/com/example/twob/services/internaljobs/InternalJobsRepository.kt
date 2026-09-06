package com.example.twob.services.internaljobs

import android.net.Uri
import com.example.twob.data.remote.NetworkResult

interface InternalJobsRepository {

    suspend fun getAvailableJobs():
            NetworkResult<List<InternalJob>>

    suspend fun getApplications():
            NetworkResult<List<InternalJobApplication>>

    suspend fun getApplicationById(
        id: Int
    ): NetworkResult<InternalJobApplication>

    suspend fun registerApplication(
        jobId: Int,
        notes: String,
        skills: String,
        resumeUri: Uri
    ): NetworkResult<Unit>

    suspend fun deleteApplication(
        id: Int
    ): NetworkResult<Unit>
}