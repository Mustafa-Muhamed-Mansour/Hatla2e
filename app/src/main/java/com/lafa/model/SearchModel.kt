package com.lafa.model

import com.google.gson.annotations.SerializedName

data class SearchModel(@SerializedName("image")
                       val image: String,
                       @SerializedName("name")
                       val name: String,
                       @SerializedName("price")
                       val price: Double)
