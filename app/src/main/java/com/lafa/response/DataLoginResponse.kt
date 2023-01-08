package com.lafa.response

import com.google.gson.annotations.SerializedName

data class DataLoginResponse(@SerializedName("id") val id: String, @SerializedName("name") val name: String, @SerializedName("email") val email: String, @SerializedName("phone") val phone: String, @SerializedName("image") val image: String, @SerializedName("token") val token: String)