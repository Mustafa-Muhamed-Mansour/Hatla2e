package com.lafa.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lafa.repositories.SearchRepository
import com.lafa.response.SearchProductResponse
import com.lafa.response.SearchResponse
import kotlinx.coroutines.launch
import javax.inject.Inject


class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {


    private val searchMutableLiveData: MutableLiveData<SearchResponse> = MutableLiveData()
    private val stringMutableLiveData: MutableLiveData<String> = MutableLiveData()


    fun searchProducts(authTokenSearchProduct: String, searchProductResponse: SearchProductResponse): LiveData<SearchResponse> {
        viewModelScope.launch {
            val response = searchRepository.searchProducts(authTokenSearchProduct, searchProductResponse)
            try {
                if (response.isSuccessful) {
                    response.body().let {
                        searchMutableLiveData.postValue(it)
                    }
                } else {
                    stringMutableLiveData.value = response.errorBody().toString()
                }
            } catch (e: Exception) {
                stringMutableLiveData.value = e.message.toString()
            }
        }
        return searchMutableLiveData
    }
}
