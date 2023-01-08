package com.lafa.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lafa.model.ProductModel
import com.lafa.network.local.ProductDatabase
import com.lafa.repositories.FavoriteRepository
import com.lafa.repositories.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {


    fun getAllProductToFavorite(): LiveData<List<ProductModel>> {
        return favoriteRepository.getAllProductToFavorite()
    }


    fun deleteProductFromFavorite(productModel: ProductModel) {
        viewModelScope.launch {
            favoriteRepository.deleteProductFromFavorite(productModel)
        }
    }

    fun insertProductToFavorite(productModel: ProductModel) {
        viewModelScope.launch {
            favoriteRepository.insertProductToFavorite(productModel)
        }
    }


}