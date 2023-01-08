package com.lafa.model

import com.google.gson.annotations.SerializedName

data class ContactModel(@SerializedName("value")
                        val value: String,
                        @SerializedName("image")
                        val image: String)
