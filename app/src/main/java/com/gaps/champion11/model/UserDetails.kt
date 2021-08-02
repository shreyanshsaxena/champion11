package com.gaps.champion11.model

import com.google.gson.annotations.SerializedName

data class UserDetails(
    @SerializedName("userName") val username: String,
    @SerializedName("phoneNumber") val phonenumber: String,
    @SerializedName("email") val email: String,
    @SerializedName("userId") val userid: String,
    @SerializedName("fullName") val fullname: String,
    @SerializedName("gender") val gender: Int,
    @SerializedName("state") val state: String

)
