package com.gaps.champion11.model

import com.google.gson.annotations.SerializedName

data class UserDetails(
    @SerializedName("username") val username: String,
    @SerializedName("phonenumber") val phonenumber: String,
    @SerializedName("email") val email: String,
    @SerializedName("userid") val userid: String
)
