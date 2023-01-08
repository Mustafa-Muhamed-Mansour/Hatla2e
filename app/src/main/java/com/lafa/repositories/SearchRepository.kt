package com.lafa.repositories

import com.lafa.network.remote.ApiService
import com.lafa.response.SearchProductResponse
import com.lafa.response.SearchResponse
import retrofit2.Response
import javax.inject.Inject


class SearchRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun searchProducts(authTokenSearchProduct: String, searchProductResponse: SearchProductResponse): Response<SearchResponse> {
        return apiService.getSearchProducts(authTokenSearchProduct, searchProductResponse)
    }
}