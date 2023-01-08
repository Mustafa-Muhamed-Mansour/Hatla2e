package com.lafa.response

import com.google.gson.annotations.SerializedName

data class DataSettingResponse(@SerializedName("data") val settingResponse: SettingResponse)
