package com.lafa.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.lafa.common.Constant
import com.lafa.model.ProductModel
import com.lafa.network.local.ProductDatabase
import com.lafa.network.remote.ApiService
import com.lafa.response.*
import retrofit2.Response
import java.net.IDN
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val apiService: ApiService,
    private val db: ProductDatabase
) {


    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val randomKey = databaseReference.database.reference.push().key.toString()


    suspend fun getBannerImages(): Response<BannerResponse> {
        return apiService.getImageBanners()
    }

    suspend fun getCategoryImages(): Response<CategoryResponse> {
        return apiService.getCategories()
    }

    suspend fun getProductImages(authToken: String): Response<ProductResponse> {
        return apiService.getImageProducts(authToken)
    }


    suspend fun insertProductToFavorite(productModel: ProductModel) {
        db.getProductDao().insertProductToFavorite(productModel)
    }

    suspend fun deleteProductFromFavorite(productModel: ProductModel) {
        db.getProductDao().deleteProductFromFavorite(productModel)
    }

    fun getAllProductToFavorite(): LiveData<List<ProductModel>> {
        return db.getProductDao().getAllProductToFavorite()
    }

    fun cartOfProduct(quantity: Int, productModel: ProductModel) {
        val model = ProductModel(productModel.id, productModel.description, productModel.discount, productModel.image, productModel.images.toList(), productModel.name, productModel.oldPrice, productModel.price, quantity)
        databaseReference
            .child(Constant.PRODUCT)
            .child(randomKey)
            .setValue(model)
    }

}