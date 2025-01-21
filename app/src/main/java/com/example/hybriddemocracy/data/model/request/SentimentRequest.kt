package com.example.hybriddemocracy.data.model.request

import com.google.gson.annotations.SerializedName

data class SentimentRequest(
    @SerializedName("billId") val billId: Long,
    @SerializedName("citizenId") val citizenId: Long,
    @SerializedName("rating") val rating: Int,
    @SerializedName("feedback") val feedback: String,
)
