package com.example.hybriddemocracy.data.model.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("jwt") val jwt: String,
)
