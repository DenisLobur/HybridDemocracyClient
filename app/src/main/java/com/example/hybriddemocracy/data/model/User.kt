package com.example.hybriddemocracy.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("citizenId")
    val id: Long,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String
)
