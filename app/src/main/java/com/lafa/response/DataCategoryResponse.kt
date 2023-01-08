package com.lafa.response

import com.google.gson.annotations.SerializedName
import com.lafa.model.CategoryModel

data class DataCategoryResponse(@SerializedName("data") val categoryModel: ArrayList<CategoryModel>)
