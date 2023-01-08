package com.lafa.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lafa.model.ProductModel
import com.lafa.repositories.HomeRepository
import com.lafa.response.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.HttpException
import javax.inject.Inject


class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val bannerMutableLiveData: MutableLiveData<BannerResponse> = MutableLiveData()
    private val categoryMutableLiveData: MutableLiveData<CategoryResponse> = MutableLiveData()
    private val homeMutableLiveData: MutableLiveData<ProductResponse> = MutableLiveData()
    private val stringMutableLiveData: MutableLiveData<String> = MutableLiveData()

    fun getBanner(): LiveData<BannerResponse> {
        viewModelScope.launch {
            try {
                val responseProduct = homeRepository.getBannerImages()
                if (responseProduct.isSuccessful) {
                    responseProduct.body().let {
                        bannerMutableLiveData.postValue(it)
                    }
                } else {
                    stringMutableLiveData.value = responseProduct.errorBody().toString()
                }
            } catch (e: Exception) {
                stringMutableLiveData.value = e.localizedMessage?.toString()
            } catch (e: HttpException) {
                stringMutableLiveData.value = e.localizedMessage?.toString()
            }
        }
        return bannerMutableLiveData
    }

    fun getCategory(): LiveData<CategoryResponse> {
        viewModelScope.launch {
            val responseCategory = homeRepository.getCategoryImages()
            try {
                if (responseCategory.isSuccessful) {
                    responseCategory.body().let {
                        categoryMutableLiveData.postValue(it)
                    }
                } else {
                    stringMutableLiveData.value = responseCategory.errorBody().toString()
                }
            } catch (e: Exception) {
                stringMutableLiveData.value = e.message.toString()
            }
        }
        return categoryMutableLiveData
    }

    fun getProduct(authToken: String): LiveData<ProductResponse> {

        viewModelScope.launch {
            val responseProduct = homeRepository.getProductImages(authToken)
            try {
                if (responseProduct.isSuccessful) {
                    responseProduct.body().let {
                        homeMutableLiveData.postValue(it)
                    }
                } else {
                    stringMutableLiveData.value = responseProduct.errorBody().toString()
                }
            } catch (e: Exception) {
                stringMutableLiveData.value = e.message.toString()
            }
        }
        return homeMutableLiveData
    }


    fun insertProductToFavorite(productModel: ProductModel) {
        viewModelScope.launch {
            homeRepository.insertProductToFavorite(productModel)
        }
    }

    fun cartOfProduct(quantity: Int, productModel: ProductModel) {
        homeRepository.cartOfProduct(quantity , productModel)
    }
}