package com.lafa.response

import com.google.gson.annotations.SerializedName

data class DataComplaint(@SerializedName("name")
                         val name: String,
                         @SerializedName("phone")
                         val phone: String,
                         @SerializedName("email")
                         val email: String,
                         @SerializedName("message")
                         val message: String)
