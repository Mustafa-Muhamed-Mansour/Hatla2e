package com.lafa.response


import com.google.gson.annotations.SerializedName

data class HomeResponse(@SerializedName("data")
                        val dataHomeResponse: DataHomeResponse
)