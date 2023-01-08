package com.lafa.response

import com.google.gson.annotations.SerializedName
import com.lafa.model.QuestionModel

data class DataQuestionResponse(@SerializedName("data") val questionModel: ArrayList<QuestionModel>)
