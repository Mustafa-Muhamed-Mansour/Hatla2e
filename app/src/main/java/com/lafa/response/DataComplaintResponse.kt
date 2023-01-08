package com.lafa.response

import com.google.gson.annotations.SerializedName

data class DataComplaintResponse(@SerializedName("message")
                                 val message: String,
                                 @SerializedName("data")
                                 val dataComplaint: DataComplaint)
