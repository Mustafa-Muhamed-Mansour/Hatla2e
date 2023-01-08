package com.lafa.response

import com.google.gson.annotations.SerializedName

data class SettingResponse(@SerializedName("about") val about: String,
                           @SerializedName("terms") val term: String)
