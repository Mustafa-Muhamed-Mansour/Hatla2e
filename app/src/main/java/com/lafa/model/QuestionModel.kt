package com.lafa.model

import com.google.gson.annotations.SerializedName

data class QuestionModel(@SerializedName("question")
                         val question: String,
                         @SerializedName("answer")
                         val answer: String)
