package com.lafa.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(@SerializedName("data")
                          val dataSearchResponse: DataSearchResponse)
