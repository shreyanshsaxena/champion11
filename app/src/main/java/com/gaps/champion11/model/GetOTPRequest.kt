package com.gaps.champion11.model

import com.google.gson.annotations.SerializedName

data class GetOTPRequest (
        @SerializedName("Username")
        val Username: String = ""
        )