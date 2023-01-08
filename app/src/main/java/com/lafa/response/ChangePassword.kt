package com.lafa.response

import com.google.gson.annotations.SerializedName

data class ChangePassword(@SerializedName("current_password")
                          val current_password: String,
                          @SerializedName("new_password")
                          val new_password: String)
