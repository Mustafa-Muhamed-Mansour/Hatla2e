package com.lafa.model

import com.google.gson.annotations.SerializedName

data class RegisterModel(
    @SerializedName("name")
    var name: String,
    @SerializedName("phone")
    var phone: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("password")
    var password: String
)


