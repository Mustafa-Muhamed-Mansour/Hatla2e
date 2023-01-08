package com.lafa.repositories

import com.lafa.network.remote.ApiService
import com.lafa.response.DataSettingResponse
import com.lafa.response.QuestionResponse
import retrofit2.Response
import javax.inject.Inject

class QuestionRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getQuestions(): Response<QuestionResponse> {
        return apiService.getQuestions()
    }

    suspend fun getSettings(): Response<DataSettingResponse> {
        return apiService.getSettings()
    }
}