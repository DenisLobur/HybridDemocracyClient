package com.example.hybriddemocracy.data.model.request

import com.google.gson.annotations.SerializedName

data class BillRequest(
    @SerializedName("rating") val rating: Int,
    @SerializedName("feedback") val feedback: String,
)
