package com.lafa.repositories

import com.lafa.model.RegisterModel
import com.lafa.network.remote.ApiService
import com.lafa.response.RegisterResponse
import retrofit2.Response
import javax.inject.Inject


class RegisterRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun createAccountUser(registerModel: RegisterModel): Response<RegisterResponse> {
        return apiService.createAccount(registerModel)
    }
}