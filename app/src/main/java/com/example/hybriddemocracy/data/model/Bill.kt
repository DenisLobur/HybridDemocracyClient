package com.example.hybriddemocracy.data.model

import com.google.gson.annotations.SerializedName

data class Bill(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("isVoted")
    val isVoted: Boolean,
    @SerializedName("date")
    val date: Int,
    @SerializedName("dokId")
    val dokId: Int,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("feedback")
    val feedback: String,
    @SerializedName("nreg")
    val nreg: String,
    @SerializedName("citizenId")
    val citizenId: Long,
) {
}