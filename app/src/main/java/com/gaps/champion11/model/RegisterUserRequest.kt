package com.gaps.champion11.model


import com.google.gson.annotations.SerializedName

data class RegisterUserRequest(
    @SerializedName("UserName")
    val userName: String = "",
    @SerializedName("FullName")
    val fullName: String = "",
    @SerializedName("Password")
    val password: String? = "",
    @SerializedName("State")
    val state: String =""

)


