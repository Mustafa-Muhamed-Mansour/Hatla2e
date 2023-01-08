package com.lafa.repositories

import com.lafa.network.remote.ApiService
import com.lafa.response.DataComplaint
import com.lafa.response.DataComplaintResponse
import retrofit2.Response
import javax.inject.Inject

class ComplaintRepository @Inject constructor(
    private val apiService: ApiService
) {


    suspend fun addNewComplaint(dataComplaint: DataComplaint): Response<DataComplaintResponse> {
        return apiService.addNewComplaints(dataComplaint)
    }


}