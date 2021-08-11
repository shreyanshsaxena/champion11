package com.gaps.champion11.model

import com.google.gson.annotations.SerializedName

data class UserStatsResponse(
    @SerializedName("userName") val username: String,
    @SerializedName("gameId") val gameId: Int,
    @SerializedName("result") val email: Int,
    @SerializedName("userId") val userid: String,
    @SerializedName("startTime") val startTime: String,
    @SerializedName("endTime") val endTime: String,
    @SerializedName("resultAmount") val resultAmount: Int,
    @SerializedName("pattiNo") val pattiNo: String,
    @SerializedName("userGameAmount") val userGameAmount: Int,
    @SerializedName("correctOption") val correctOption: String
)
