package com.lafa.model

import com.google.gson.annotations.SerializedName

data class CategoryModel(@SerializedName("image")
                         val image: String,
                         @SerializedName("name")
                         val name: String)
