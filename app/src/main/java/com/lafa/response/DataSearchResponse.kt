package com.lafa.response

import com.google.gson.annotations.SerializedName
import com.lafa.model.SearchModel

data class DataSearchResponse(@SerializedName("data")
                              val searchModel: ArrayList<SearchModel>)
