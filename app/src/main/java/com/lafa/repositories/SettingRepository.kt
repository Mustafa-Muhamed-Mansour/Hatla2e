package com.lafa.repositories

import com.lafa.network.remote.ApiService
import com.lafa.response.ContactResponse
import com.lafa.response.LoginResponse
import com.lafa.response.LogoutResponse
import retrofit2.Response
import javax.inject.Inject

class SettingRepository @Inject constructor(
    private val apiService: ApiService
) {


    suspend fun profileUser(authToken: String): Response<LoginResponse> {
        return apiService.profileAccount(authToken)
    }

    suspend fun getContact(): Response<ContactResponse> {
        return apiService.getContacts()
    }

    suspend fun logoutUser(authTokenLogout: String): Response<LogoutResponse> {
        return apiService.logoutAccount(authTokenLogout)
    }
}