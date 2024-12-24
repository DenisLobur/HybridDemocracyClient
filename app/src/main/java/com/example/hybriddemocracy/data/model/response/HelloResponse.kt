package com.example.hybriddemocracy.data.model.response

import com.google.gson.annotations.SerializedName

data class HelloResponse(
    @SerializedName("message") val message: String,
)
