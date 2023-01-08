package com.lafa.repositories

import androidx.lifecycle.LiveData
import com.lafa.model.ProductModel
import javax.inject.Inject

class FavoriteRepository @Inject constructor(
    private val homeRepository: HomeRepository
) {

    fun getAllProductToFavorite(): LiveData<List<ProductModel>> {
        return homeRepository.getAllProductToFavorite()
    }

    suspend fun deleteProductFromFavorite(productModel: ProductModel) {
        homeRepository.deleteProductFromFavorite(productModel)
    }

    suspend fun insertProductToFavorite(productModel: ProductModel) {
        homeRepository.insertProductToFavorite(productModel)
    }

}