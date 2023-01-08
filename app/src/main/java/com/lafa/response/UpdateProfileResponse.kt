package com.lafa.response

import com.google.gson.annotations.SerializedName
import com.lafa.model.RegisterModel

class UpdateProfileResponse
{
    @SerializedName("message")
    lateinit var message: String
    lateinit var registerModel: RegisterModel
}