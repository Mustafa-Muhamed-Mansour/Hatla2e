package com.lafa.response


import com.google.gson.annotations.SerializedName

data class ProductResponse(@SerializedName("data")
                           val dataProductResponse: DataProductResponse
)