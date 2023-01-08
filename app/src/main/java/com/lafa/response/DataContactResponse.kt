package com.lafa.response

import com.google.gson.annotations.SerializedName
import com.lafa.model.ContactModel

data class DataContactResponse(@SerializedName("data") val contactModel: ArrayList<ContactModel>)
