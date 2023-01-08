package com.lafa.repositories

import com.lafa.model.RegisterModel
import com.lafa.network.remote.ApiService
import com.lafa.response.*
import retrofit2.Response
import retrofit2.http.Body
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun profileUser(authToken: String): Response<LoginResponse> {
        return apiService.profileAccount(authToken)
    }

    suspend fun updateProfile(authTokenUpdate: String, registerModel: RegisterModel): Response<UpdateProfileResponse> {
        return apiService.updateProfile(authTokenUpdate, registerModel)
    }

    suspend fun changePassword(authTokenChange: String, changePassword: ChangePassword): Response<ChangePasswordResponse> {
        return apiService.changePassword(authTokenChange, changePassword)
    }

}