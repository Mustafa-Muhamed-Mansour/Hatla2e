package com.lafa.response

import com.google.gson.annotations.SerializedName
import com.lafa.model.RegisterModel


class RegisterResponse {
    @SerializedName("message")
    val message: String = ""
    @SerializedName("status")
    var status: Boolean = false
    lateinit var registerModel: RegisterModel
}