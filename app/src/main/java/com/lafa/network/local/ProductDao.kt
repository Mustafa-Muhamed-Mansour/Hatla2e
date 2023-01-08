package com.lafa.network.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.lafa.model.ProductModel

@Dao
interface ProductDao {

    @Insert
    suspend fun insertProductToFavorite(productModel: ProductModel)

    @Delete
    suspend fun deleteProductFromFavorite(productModel: ProductModel)

    @Query("select * from product")
    fun getAllProductToFavorite(): LiveData<List<ProductModel>>


}