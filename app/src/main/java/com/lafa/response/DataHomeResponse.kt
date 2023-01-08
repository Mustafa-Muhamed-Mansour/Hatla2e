package com.lafa.response


import com.google.gson.annotations.SerializedName
import com.lafa.model.BannerModel
import com.lafa.model.ProductModel

data class DataHomeResponse(@SerializedName("ad")
                            val ad: String,
                            @SerializedName("banners")
                            val banners: ArrayList<BannerModel>,
                            @SerializedName("products")
                            val products: ArrayList<ProductModel>
)