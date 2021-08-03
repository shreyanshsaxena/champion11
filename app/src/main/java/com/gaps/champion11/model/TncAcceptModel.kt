package com.gaps.champion11.model

import com.google.gson.annotations.SerializedName
import retrofit2.http.Query

data class TncAcceptModel(@SerializedName("Username") val username:String?,@SerializedName("AcceptedTnC") val acceptedTnc:Boolean)