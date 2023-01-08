package com.lafa.response

import com.google.gson.annotations.SerializedName
import com.lafa.model.LoginModel

class LoginResponse {
    @SerializedName("data")
    lateinit var dataLoginResponse: DataLoginResponse
    @SerializedName("status")
    var status: Boolean = false
    @SerializedName("message")
    lateinit var message: String
//    lateinit var loginModel: LoginModel
}
//{
//
//}
