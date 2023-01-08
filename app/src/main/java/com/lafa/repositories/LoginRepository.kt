package com.lafa.repositories

import com.lafa.model.LoginModel
import com.lafa.network.remote.ApiService
import com.lafa.response.LoginResponse
import retrofit2.Response
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private var apiService: ApiService
) {


    suspend fun loginUser(loginModel: LoginModel): Response<LoginResponse> {
        return apiService.loginAccount(loginModel)
    }

}