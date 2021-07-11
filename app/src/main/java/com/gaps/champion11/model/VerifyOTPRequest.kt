package com.gaps.champion11.model

import com.google.gson.annotations.SerializedName

data class VerifyOTPRequest (
    @SerializedName("Username")
    val Username: String = "",
    @SerializedName("Otp")
    val Otp: String = "",
)