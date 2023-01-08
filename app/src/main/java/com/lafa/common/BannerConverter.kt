package com.lafa.common

import androidx.room.TypeConverter
import com.google.gson.Gson

class BannerConverter {

    @TypeConverter
    fun fromGsonToImageBannerConverter(value: String): List<String> {
//        val listType = object : TypeToken<List<ProductModel>>(){}.type // listType
        return Gson().fromJson(value, Array<String>::class.java).toList()
    }

    @TypeConverter
    fun fromImageBannerToGsonConverter(imageBanner: List<String?>): String {
        return Gson().toJson(imageBanner)
    }

    // Gson == String
}