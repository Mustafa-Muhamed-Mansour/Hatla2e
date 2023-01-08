package com.lafa.response


import com.google.gson.annotations.SerializedName
import com.lafa.model.ProductModel

data class DataProductResponse(@SerializedName("data")
                               val productModel: ArrayList<ProductModel>
)