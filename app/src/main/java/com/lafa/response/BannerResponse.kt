package com.lafa.response


import com.google.gson.annotations.SerializedName
import com.lafa.model.BannerModel

data class BannerResponse(
    @SerializedName("data")
    val bannerModel: ArrayList<BannerModel>
)